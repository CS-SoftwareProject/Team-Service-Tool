package TT.dao.timeline;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.timeline.TimelineComments;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;

public class TimelineCommentsDAO {
  private static final Logger logger = LoggerFactory.getLogger(TimelineDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public void addTimelineComment(TimelineComments timelineComment) {
    String sql = "insert into comments (userId, Timeline_Num, TimeLineContent_Num, comment_Content) values (?, ?, ?, ?)";

    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, timelineComment.getUserId());
        pstmt.setInt(2, timelineComment.getTimeline_Num());
        pstmt.setInt(3, timelineComment.getTimeLineContent_Num());
        pstmt.setString(4, timelineComment.getComment_Content());
      }
    });
  }

  public void removeTimelineComment(int num) {
    String sql = "delete from comments where comment_Num = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, num);
      }
    });
  }

  public List<TimelineComments> getContent(int TimeLine_Num) {
    String sql = "select * from comments where Timeline_Num = ? ORDER by comment_Num DESC";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, TimeLine_Num);
      }
    }, new RowMapper() {
      @Override
      public TimelineComments mapRow(ResultSet rs) throws SQLException {
        TimelineComments comment = new TimelineComments();
        comment.setComment_Num(rs.getInt("comment_Num"));
        comment.setTimeline_Num(rs.getInt("Timeline_Num"));
        comment.setUserId(rs.getString("userId"));
        comment.setTimeLineContent_Num(rs.getInt("TimeLineContent_Num"));
        comment.setComment_Content(rs.getString("comment_Content"));
        comment.setComment_date(rs.getString("comment_date"));
        return comment;
      }
    });
  }
}
