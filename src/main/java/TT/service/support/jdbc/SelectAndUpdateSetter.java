package TT.service.support.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SelectAndUpdateSetter {
  void setParametersBySelect(PreparedStatement pstmt, ResultSet rs) throws SQLException;
}
