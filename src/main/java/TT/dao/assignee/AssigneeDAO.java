package TT.dao.assignee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.assignee.Assignee;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;
import TT.service.support.jdbc.SelectAndSelectSetter;

public class AssigneeDAO {
  private static final Logger logger = LoggerFactory.getLogger(AssigneeDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public int addAssignee(String assigneeMember, String projectName, String roleName, int boardNum, int cardNum) {
    String sql = "insert into assignees (PM_Num, roleNum, Card_Num) values (" + "(select PM_Num from project_members where userId = ? && Project_Name = ?)," + "(select roleNum from roles where roleName = ? and Board_Num = ?), ?)";
    return jdbc.generatedExecuteUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, assigneeMember);
        pstmt.setString(2, projectName);
        pstmt.setString(3, roleName);
        pstmt.setInt(4, boardNum);
        pstmt.setInt(5, cardNum);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
          return rs.getInt(1);
        }
        return null;
      }
    });
  }

  public void addAssignee(int projectMemberNum, int roleNum, int cardNum) {
    String sql = "insert into assignees (PM_Num,roleNum,Card_Num) value(?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, projectMemberNum);
        pstmt.setInt(2, roleNum);
        pstmt.setInt(3, cardNum);
      }
    });
  }

  public void removeAssignee(int assigneeNum) {
    String sql = "delete from assignees where assigneeNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, assigneeNum);
      }
    });
  }

  public void updateAssignee(int assigneeNum, int pmNum, int roleNum) {
    String sql = "update assignees set PM_Num = ?, roleNum = ? where assigneeNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, pmNum);
        pstmt.setInt(2, roleNum);
        pstmt.setInt(3, assigneeNum);
      }
    });
  }

  public Assignee getAsiggnee(int pmNum, int roleNum, int assigneeNum) {
    String sql = "select assigneeNum, (project_members.userId) as member, roles.roleName " + "from assignees, project_members, roles " + "where project_members.PM_Num = ? && assigneeNum = ? && roles.roleNum = ? && roles.roleNum = assignees.roleNum;";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, pmNum);
        pstmt.setInt(2, assigneeNum);
        pstmt.setInt(3, roleNum);
      }
    }, new RowMapper() {
      @Override
      public Assignee mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
          return new Assignee(rs.getInt("assigneeNum"), rs.getString("member"), rs.getString("roleName"));
        }
        return null;
      }
    });
  }

  public List<Assignee> getAssigneeList(int cardNum) {
    String sql = "select * from assignees where Card_Num = ?";
    String sql2 = "select assigneeNum, Card_Num, roleNum, (select roleName from roles where roleNum = ?) as roleName, " + "(select userId from project_members where PM_Num = ?) as userId from assignees where roleNum = ? and PM_Num = ?";
    return jdbc.selectAndSelect(sql, sql2, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, cardNum);
      }
    }, new SelectAndSelectSetter() {
      @Override
      public void setParametersBySelect(PreparedStatement pstmt, ResultSet rs) throws SQLException {
        pstmt.setInt(1, rs.getInt("roleNum"));
        pstmt.setInt(2, rs.getInt("PM_Num"));
        pstmt.setInt(3, rs.getInt("roleNum"));
        pstmt.setInt(4, rs.getInt("PM_Num"));
      }
    }, new RowMapper() {
      @Override
      public Assignee mapRow(ResultSet rs) throws SQLException {
        return new Assignee(rs.getInt("assigneeNum"), rs.getInt("Card_Num"), rs.getString("roleName"), rs.getInt("roleNum"), rs.getString("userId"));
      }
    });
  }

  public List<Assignee> getAssignees(int cardNum) {
    String sql = "select * from assignees where Card_Num=?";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, cardNum);
      }
    }, new RowMapper() {
      @Override
      public Assignee mapRow(ResultSet rs) throws SQLException {
        return new Assignee(rs.getInt("assigneeNum"), rs.getInt("PM_Num"), rs.getInt("roleNum"), rs.getInt("Card_Num"));
      }
    });
  }

  public Assignee findByAssigneeNum(String projectName) {
    String sql = "select Card_Num, project_members.userId, roles.roleName from assignees, roles, project_members where assignees.PM_Num = project_members.PM_Num && assignees.roleNum = roles.roleNum && Project_Name = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Assignee mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
          Assignee assignee = new Assignee();
          assignee.setCardNum(rs.getInt("Card_Num"));
          assignee.setUserId(rs.getString("userId"));
          assignee.setRoleName(rs.getString("roleName"));
          return assignee;
        }
        return null;
      }
    });
  }
}
