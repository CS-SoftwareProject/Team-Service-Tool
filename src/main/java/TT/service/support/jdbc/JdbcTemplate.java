package TT.service.support.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JdbcTemplate {
  private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);
  private Connection conn;
  private PreparedStatement pstmt;
  private PreparedStatement pstmt2;
  public ResultSet rs;
  public ResultSet rs2;

  public Connection getConnection() {
    Properties props = new Properties();
    InputStream in = getClass().getResourceAsStream("/db.properties");
    try {
      props.load(in);
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String driver = props.getProperty("jdbc.driver");
    String url = props.getProperty("jdbc.url");
    String username = props.getProperty("jdbc.username");
    String password = props.getProperty("jdbc.password");
    try {
      Class.forName(driver);
      return DriverManager.getConnection(url, username, password);
    } catch (Exception e) {
      logger.debug(e.getMessage());
      return null;
    }
  }

  public void sourceReturn() {
    try {
      if (this.conn != null) {
        conn.close();
      }
      if (this.pstmt != null) {
        pstmt.close();
      }
      if (this.pstmt2 != null) {
        pstmt2.close();
      }
      if (this.rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      logger.debug("sourceReturn Error:" + e.getMessage());
    }
  }
}
