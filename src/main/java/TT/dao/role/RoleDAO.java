package TT.dao.role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.role.Role;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;

public class RoleDAO {
  private static final Logger logger = LoggerFactory.getLogger(RoleDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public Role addRole(String roleName, int boardNum) {
    String sql = "insert into roles (roleName, Board_Num) values(?,?)";
    return jdbc.generatedExecuteUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, roleName);
        pstmt.setInt(2, boardNum);
      } 
    }, new RowMapper() {
      @Override
      public Role mapRow(ResultSet rs) throws SQLException {
        if(rs.next()) {
          return new Role(rs.getInt(1), roleName, boardNum);
        }
        return null;
      }
    });
  }
  
  public void deleteRole(int roleNum) {
    String sql = "delete from roles where roleNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, roleNum);
      }
    });
  }
  
  public void updateRole(int roleNum, String newRoleName) {
    String sql = "update roles set roleName = ? where roleNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, newRoleName);
        pstmt.setInt(2, roleNum);
      }
    });
  }
  
  public List getRoleList(int boardNum) {
    String sql = "select * from roles where Board_Num = ?";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
      }
    }, new RowMapper() {
      @Override
      public Role mapRow(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleNum(rs.getInt("roleNum"));
        role.setRoleName(rs.getString("roleName"));
        role.setBoardNum(rs.getInt("Board_Num"));
        return role;
      }
    }); 
  }
}
