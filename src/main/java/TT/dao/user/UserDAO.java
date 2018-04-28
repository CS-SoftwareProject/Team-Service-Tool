package TT.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.user.User;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;

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
}
