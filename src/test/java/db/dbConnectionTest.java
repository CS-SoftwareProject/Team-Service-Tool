package db;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import TT.service.support.jdbc.JdbcTemplate;

public class dbConnectionTest {
  @Test
  public void jdbcConnection() {
    JdbcTemplate jdbc = new JdbcTemplate();
    assertNotNull(jdbc.getConnection());
  }
}
