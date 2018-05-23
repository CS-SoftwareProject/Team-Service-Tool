package TT.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.user.User;
import TT.domain.user.UserDashBoard;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;
import TT.service.support.jdbc.SelectAndSelectSetter;

public class UserDAO {
  private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public void addUser(User user) {
    String sql = "insert into users values(?,?,?,?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getBirth());
        pstmt.setString(5, user.getEmail());
        pstmt.setString(6, user.getImage());
      }
    });
  }

  public User getByUserId(String userId) {
    String sql = "select * from users where userId = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    }, new RowMapper() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        while (!rs.next())
          return null;
        return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("birth"), rs.getString("email"), rs.getString("image"));
      }
    });
  }

  public void removeUser(String userId) throws SQLException {
    String sql = "delete from users where userId = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    });
  }

  public void updateUser(User user) throws SQLException {
    logger.debug("user: {}",user);
    String sql = "update users set password = ?, name = ?, birth = ?, email = ? where userId = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getBirth());
        pstmt.setString(4, user.getEmail());
        pstmt.setString(5, user.getUserId());
      }
    });
  }
  
  public void updateUserWithFile(User user) throws SQLException {
    String sql = "update users set password = ?, name = ?, birth = ?, email = ?, image= ? where userId = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getBirth());
        pstmt.setString(4, user.getEmail());
        pstmt.setString(5, user.getImage());
        pstmt.setString(6, user.getUserId());
      }
    });
  }
  
  public List<UserDashBoard> getUserDashBoardData(int projectMemberNum) {
    String sql = "select * from assignees where PM_Num = ?";
    String sql2 = "select assigneeNum, (select roleName from roles where roleNum = ?) as roleName,"
                + "(select Board_Num from roles where roleNum = ?) as boardNum,"
                + "(select SubJect from cards where Card_Num = ?) as cardName,"
                + "(select ?) as cardNum,"
                + "(select progress from cards where Card_Num = ?) as progress from assignees where PM_Num = ?";
    return jdbc.selectAndSelect(sql, sql2, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, projectMemberNum);
      }
    }, new SelectAndSelectSetter() {
      @Override
      public void setParametersBySelect(PreparedStatement pstmt, ResultSet rs) throws SQLException {
        pstmt.setInt(1, rs.getInt("roleNum"));
        pstmt.setInt(2, rs.getInt("roleNum"));
        pstmt.setInt(3, rs.getInt("Card_Num"));
        pstmt.setInt(4, rs.getInt("Card_Num"));
        pstmt.setInt(5, rs.getInt("Card_Num"));
        pstmt.setInt(6, projectMemberNum);
      }
    }, new RowMapper() {
      @Override
      public UserDashBoard mapRow(ResultSet rs) throws SQLException {
          return new UserDashBoard(rs.getString("cardName"), rs.getString("roleName"), rs.getInt("progress"), rs.getInt("boardNum"), rs.getInt("cardNum"));
      }
    });
  }
  
  public String getImageByUserId(String userId) {
    String sql = "select image from users where userId = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    }, new RowMapper() {
      @Override
      public String mapRow(ResultSet rs) throws SQLException {
        logger.debug("rs ? " + rs.next());
        return rs.getString("image");
      }
    });
  }
}
