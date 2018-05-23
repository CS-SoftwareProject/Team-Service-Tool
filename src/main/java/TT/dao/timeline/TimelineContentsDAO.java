package TT.dao.timeline;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.timeline.TimelineContents;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;

public class TimelineContentsDAO {
  private static final Logger logger = LoggerFactory.getLogger(TimelineDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public void addTimelineContent(TimelineContents timelineContent) {
    String sql = "insert into timelinecontents (Timeline_Num, userId, TimeLineContent) values (?, ?, ?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, timelineContent.getTimeline_Num());
        pstmt.setString(2, timelineContent.getUserId());
        pstmt.setString(3, timelineContent.getTimeLineContent());
      }
    });
  }

  public void removeTimelineContent(int num) {
    String sql = "delete from timelinecontents where TimeLineContent_Num = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, num);
      }
    });
  }

  public List<TimelineContents> getContent(int Timeline_Num) {
    String sql = "select * from timelinecontents where Timeline_Num = ? ORDER by TimeLineContent_Num DESC";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, Timeline_Num);
      }
    }, new RowMapper() {
      @Override
      public TimelineContents mapRow(ResultSet rs) throws SQLException {
        TimelineContents timelinecontent = new TimelineContents();
        timelinecontent.setTimeLineContent_Num(rs.getInt("TimeLineContent_Num"));
        timelinecontent.setTimeline_Num(rs.getInt("Timeline_Num"));
        timelinecontent.setUserId(rs.getString("userId"));
        timelinecontent.setTimeLineContent(rs.getString("TimeLineContent"));
        timelinecontent.setTimeLineContent_date(rs.getString("TimeLineContent_date"));
        return timelinecontent;
      }
    });
  }
}
