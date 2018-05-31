package TT.dao.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.project.Chat;
import TT.domain.project.Image;
import TT.domain.project.Project;
import TT.domain.project.ProjectMember;
import TT.domain.user.User;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;

public class ProjectDAO {
  private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public List getProjectMemberList(String projectName) {
    List list = new ArrayList();
    String sql = "select PM_Num, users.userId, image, name, Project_Name, Power from project_members,users where Project_Name=? && project_members.userId=users.userId && project_members.participation = ?";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
        pstmt.setInt(2, 1);
      }
    }, new RowMapper() {
      @Override
      public ProjectMember mapRow(ResultSet rs) throws SQLException {
        ProjectMember pm = new ProjectMember();
        pm.setNum(rs.getInt("PM_Num"));
        pm.setUserId(rs.getString("userId"));
        pm.setUserImage(rs.getString("image"));
        pm.setUserName(rs.getString("name"));
        pm.setProjectName(rs.getString("project_Name"));
        pm.setPower(rs.getInt("Power"));
        return pm;
      }
    });
  }

  public List<Project> getProjectList(String userId) {
    String sql = "select * from projects where Project_Name in (select Project_Name from project_members where userId=? && participation = ?)";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
        pstmt.setInt(2, 1);
      }
    }, new RowMapper() {
      @Override
      public Project mapRow(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectName(rs.getString("Project_Name"));
        project.setProjectDate(rs.getDate("Project_Date"));
        return project;
      }
    });
  }

  public void addProject(Project project) {
    Timestamp date = new Timestamp(new Date().getTime());
    String sql = "insert into projects (Project_name,Project_Date) values (?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, project.getProjectName());
        pstmt.setTimestamp(2, date);
      }
    });
  }

  public void addprojectMember(Project project, User user, int Power) {
    String sql = "insert into project_members (userId, Project_Name, Power, participation) values (?,?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, project.getProjectName());
        pstmt.setInt(3, Power);
        pstmt.setInt(4, 1);
      }
    });
  }

  public void removeProject(String projectName) {
    String sql = "delete from projects where Project_name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    });
  }

  public void updateProject(String newName, String preName) {
    String sql = "update projects set Project_Name = ?, Project_Date = ? where Project_Name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, newName);
        pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
        pstmt.setString(3, preName);
      }
    });
  }

  public void addImage(Image image, String projectName) {
    String sql = "insert into imagechats(Image_Path,Project_Name,Author) values(?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, image.getImagePath());
        pstmt.setString(2, projectName);
        pstmt.setString(3, image.getAuthor());
      }
    });
  }

  public void removeImage(String Image_Path) {
    String sql = "delete from imagechats where Image_Path=?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, Image_Path);
      }
    });
  }

  public List getImageList(String projectName) {
    String sql = "select Image_Path from imagechats where Project_Name= ? order by ImageChat_Time asc";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public String mapRow(ResultSet rs) throws SQLException {
        return rs.getString("Image_Path").toString();
      }
    });
  }

  public List getUserList(String keyword, String projectName) {
    //String sql = "select * from users where not userId In (select userId from project_members where Project_Name = ?)";
    String sql = "select userId, name, birth, image from users where userId like '" + keyword.trim() + "%'" + " && not userId in (select userId from project_members where participation = ? && Project_Name = ?)";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, 1);
        pstmt.setString(2 , projectName);
      }
    }, new RowMapper() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString("userId"));
        user.setName(rs.getString("name"));
        user.setBirth(rs.getString("birth"));
        user.setImage(rs.getString("image"));
        logger.debug("user : {}", user);
        return user;
      }
    });
  }

  public void InviteUser(String userId, String projectName, int power) {
    String sql = "insert into project_members (userId, Project_Name, Power) values (?, ?, ?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
        pstmt.setString(2, projectName);
        pstmt.setInt(3, power);
      }
    });
  }

  public void removeProjectMember(String userId, String projectName) {
    String sql = "delete from project_members where Project_Name = ? and userId = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
        pstmt.setString(2, userId);
      }
    });
  }

  public Project getByProjectName(String projectName) {
    String sql = "select * from projects where Project_name = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Project mapRow(ResultSet rs) throws SQLException {
        while (!rs.next())
          return null;
        return new Project(rs.getString("Project_name"), rs.getDate("Project_Date"));
      }
    });
  }

  public User getProjectMember(String projectName) {
    String sql = "select * from project_members where Project_name = ? && participation = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
        pstmt.setInt(2, 1);
      }
    }, new RowMapper() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        while (!rs.next())
          return null;
        return new User(rs.getString("userId"));
      }
    });
  }

  public Image getByImageNum(int imageNum) {
    String sql = "select * from imagechats where Image_Num = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, imageNum);
      }
    }, new RowMapper() {
      @Override
      public Image mapRow(ResultSet rs) throws SQLException {
        if (!rs.next()) {
          return null;
        }
        return new Image(rs.getString("Image_Path"), rs.getString("Author"));
      }
    });
  }

  public int getImageNum(String projectName, String author) {
    String sql = "select * from imagechats where Project_Name = ? and Author = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
        pstmt.setString(2, author);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (!rs.next()) {
          return 0;
        }
        return rs.getInt("Image_Num");
      }
    });
  }

  public void addChatMessage(Chat chat) {
    logger.debug("ProjectDAO addChatMessage - Chat: {}", chat);
    String sql = "insert into chats(chatMessage,chatTime,writeUser,Project_Name) values(?,?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, chat.getChatMessage());
        pstmt.setLong(2, chat.getChatTime());
        pstmt.setString(3, chat.getWriteUser());
        pstmt.setString(4, chat.getProjectName());
      }
    });
  }

  public List<Chat> getChatMessages(String projectName) {
    String sql = "select chatMessage,chatTime,chats.writeUser,users.image from chats,users where chats.writeUser=users.userId && Project_Name=? order by chatTime asc";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public List<Chat> mapRow(ResultSet rs) throws SQLException {
        List<Chat> chatList = new LinkedList<>();
        while (rs.next()) {
          chatList.add(new Chat(rs.getString("writeUser"), rs.getString("image"), rs.getLong("chatTime"), rs.getString("chatMessage")));
        }
        return chatList;
      }
    });
  }

  public List<ProjectMember> getProjectDashMember(String projectName) {
    String sql = "select userId, image from users where userId in (select userId from project_members where Project_Name = ? && participation = ?)";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
        pstmt.setInt(2, 1);
      }
    }, new RowMapper() {
      @Override
      public ProjectMember mapRow(ResultSet rs) throws SQLException {
        return new ProjectMember(rs.getString("userId"), rs.getString("image"));
      }
    });
  }

  public int getProjectProgress(String projectName) {
    String sql = "select ROUND(AVG(progress)) as projectProgress from boards where Project_Name = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
          return rs.getInt("projectProgress");
        }
        return null;
      }
    });
  }

  public int getProjectNumByUserId(String userId) {
    String sql = "select PM_Num from project_members where userId = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (!rs.next()) {
          return 0;
        }
        return rs.getInt("PM_Num");
      }
    });
  }
  
  public void updatePrjMemberIsJoin(String joiner, String projectName) {    
    String sql = "update project_members set participation = ? where userId = ? && Project_Name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, 1);
        pstmt.setString(2, joiner);
        pstmt.setString(3, projectName);
      }
    });
  }
  
  public String getInvitedUserId(String userId) {
    String sql = "select userId from project_members where userId = ? && participation = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
        pstmt.setInt(2, 0);
      }
    }, new RowMapper() {
      @Override
      public String mapRow(ResultSet rs) throws SQLException {
        if(rs.next()) return rs.getString("userId");
        return null;
      }
    });
  }
}
