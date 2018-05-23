package TT.dao.message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.message.Message;
import TT.domain.user.User;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;
public class MessageDAO {
  private static final Logger logger = LoggerFactory.getLogger(MessageDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();
  
  public void addMessage(Message msg) {
    String sql = "insert into messages (sender, receiver, subject, content) values (?,?,?,?)";
    logger.debug("msg Obj : {}", msg);
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, msg.getSender());
        pstmt.setString(2, msg.getReceiver());
        pstmt.setString(3, msg.getSubject());
        pstmt.setString(4, msg.getContent());
      }
    });
  }
  
  public void removeMessage(int msgNum) {
    String sql = "delete from messages where msgNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, msgNum);
      }
    });
  }
  
  public void updateMessageContent (String content, int msgNum) {
    String sql = "update messages set content = ? where msgNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, content);
        pstmt.setInt(2, msgNum);
      }
    });
  }
  
  public void isRead(int msgNum) {
    String sql = "update messages set readed = ? where msgNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, 1);
        pstmt.setInt(2, msgNum);
      }
    });
  }
  
  public Message getByMessageNum(int msgNum) {
    String sql = "select * from messages where msgNum = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, msgNum);
      }
    }, new RowMapper() {
      @Override
      public Message mapRow(ResultSet rs) throws SQLException {
        if(rs.next()) {
        return new Message(rs.getInt("msgNum"),
                         rs.getString("sender"),
                         rs.getString("receiver"),
                         rs.getString("subject"),
                         rs.getString("content"),
                         rs.getDate("sendTime"), 
                         rs.getInt("readed"));
        }
        return null;
      }
    });
  }
  
  public List<Message> getMessageList(String userId) {
    String sql = "select * from messages where receiver = ? order by sendTime desc";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    }, new RowMapper() {
      @Override
      public Message mapRow(ResultSet rs) throws SQLException {
        return new Message(rs.getInt("msgNum"),
                           rs.getString("sender"), 
                           rs.getString("receiver"), 
                           rs.getString("subject"), 
                           rs.getString("content"), 
                           rs.getDate("sendTime"), 
                           rs.getInt("readed"));
      }
    });
  }
  
  public List<Message> getSendMessageList(String userId) {
    String sql = "select * from messages where sender = ? order by sendTime desc";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    }, new RowMapper() {
      @Override
      public Message mapRow(ResultSet rs) throws SQLException {
        return new Message(rs.getInt("msgNum"),
            rs.getString("sender"), 
            rs.getString("receiver"), 
            rs.getString("subject"), 
            rs.getString("content"), 
            rs.getDate("sendTime"), 
            rs.getInt("readed"));
      }
    });
  }
  
  public int getNotReadMessage(String userId) {
    String sql = "select readed from messages where readed = ? && receiver = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, 0);
        pstmt.setString(2, userId);
      }
    }, new RowMapper() {
      int count = 0;
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        while (rs.next()) {
          count++;
        }
        return count;
      }
    });
  }
  
  public List<User> getRealTimeUserList(String keyword) {
    String sql = "select userId, name, image from users where userId like '" + keyword.trim() + "%'";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        
      }
    }, new RowMapper() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString("userId"));
        user.setName(rs.getString("name"));
        user.setImage(rs.getString("image"));
        return user; 
      }
    });
  }
  
  public List<Message> getLastMessageList(String receiver) {
    String sql = "select msgNum, sender, subject, content, case when datediff(now(), sendTime) = 0"
              + " then '오늘' else concat(datediff(now(), sendTime), ' 일전') end as diff"
              + " from messages where receiver = ? && readed = ? order by sendTime desc limit 2";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, receiver);
        pstmt.setInt(2, 0);
      }
    }, new RowMapper() {
      @Override
      public Message mapRow(ResultSet rs) throws SQLException {
          Message message = new Message();
          message.setMsgNum(rs.getInt("msgNum"));
          message.setSender(rs.getString("sender"));
          message.setSubject(rs.getString("subject"));
          message.setContent(rs.getString("content"));
          message.setDateDiff(rs.getString("diff"));
          return message;
      }
    });
  }
}
