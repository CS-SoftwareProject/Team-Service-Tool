package TT.service.support.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
  PreparedStatement pstmt = null;

  void setParameters(PreparedStatement pstmt) throws SQLException;

}
