package TT.dao.timeline;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.timeline.Timeline;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;

public class TimelineDAO {
  private static final Logger logger = LoggerFactory.getLogger(TimelineDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public void addTimeline(String projectName) {
    String sql = "insert into timelines (Project_Name) values (?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    });
  }

  public Timeline getTimeline(String projectName) {
    String sql = "select * from timelines where Project_Name = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Timeline mapRow(ResultSet rs) throws SQLException {
        Timeline timeline = new Timeline();
        if (rs.next()) {
          timeline.setProjectName(rs.getString("Project_Name"));
          timeline.setTimelineNum(rs.getInt("Timeline_Num"));
          return timeline;
        }
        return null;
      }
    });
  }
}