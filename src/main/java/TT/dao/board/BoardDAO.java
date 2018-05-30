package TT.dao.board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.domain.board.Board;
import TT.domain.board.BoardActivityLog;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;


public class BoardDAO {
  private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public List<Board> getBoardList(String projectName) throws SQLException {
    String sql = "select * from boards where Project_Name = ?";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Board mapRow(ResultSet rs) throws SQLException {
        Board board = new Board();
        return new Board(rs.getInt("Board_Num"), rs.getString("Project_Name"), rs.getString("Board_Name"), rs.getInt("progress"), rs.getString("boardInfo"));
      }
    });
  }

  public int addBoard(Board board) throws SQLException {
    String sql = "insert into boards (Project_Name, Board_Name, boardInfo) values (?,?,?)";
    return jdbc.generatedExecuteUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, board.getProjectName());
        pstmt.setString(2, board.getBoardName());
        pstmt.setString(3, board.getBoardInfo());
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (rs.next())
          return rs.getInt(1);
        return null;
      }
    });
  }

  public void removeBoard(String boardName) throws SQLException {
    String sql = "delete from boards where Board_Name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, boardName);
      }
    });
  }

  public void updateBoard(String newName, String preName) throws SQLException {
    String sql = "update boards set Board_Name = ? where Board_Name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, newName);
        pstmt.setString(2, preName);
      }
    });
  }

  public Board findByBoardNum(int boardNum) {
    String sql = "select * from boards where Board_Num = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
      }
    }, new RowMapper() {
      @Override
      public Board mapRow(ResultSet rs) throws SQLException {
        while (!rs.next())
          return null;
        return new Board(rs.getString("Board_Name"), rs.getString("Project_Name"), rs.getString("boardInfo"));
      }
    });
  }

  public int getBoardNum(String boardName, String projectName) throws SQLException {
    String sql = "select * from boards where Board_Name = ? and Project_Name = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, boardName);
        pstmt.setString(2, projectName);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
          return rs.getInt("Board_Num");
        }
        return 0;
      }
    });
  }

  public void updateBoardProgress(int listNum) {
    String sql = "update boards set progress = (select round(avg(progress),0) from cards where List_Num in (select List_Num from lists where Board_Num in (select Board_Num from lists Where List_Num = ?) ) and not progress = -1) where Board_Num in (select Board_Num from lists where List_Num = ?);";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, listNum);
        pstmt.setInt(2, listNum);
      }
    });
  }

  public void reloadBoardProgress(int boardNum) {
    String sql = "update boards set progress = (select round(avg(progress),0) from cards where List_Num in (select List_Num from lists where Board_Num = ?) and not progress = -1) where Board_Num = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
        pstmt.setInt(2, boardNum);
      }
    });
  }

  public void addBoardActivity(int boardNum) {
    String sql = "insert into boardactivity (Board_Num) values (?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
      }
    });
  }

  public void addBoardActivityLog(String activity, int boardNum) {
    String sql = "insert into boardactivitylog (activityNum, activity) values ((select activityNum from boardactivity where Board_Num = ?), ?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
        pstmt.setString(2, activity);
      }
    });
    logger.debug("[INFO] BoardActivityLog add Success..");
  }

  public List<BoardActivityLog> findByStringTypeDate(String stringDate, int boardNum) {
    String sql = "select * from boardactivitylog where mid(activityDate, 1, 10) = ? && activityNum = (select activityNum from boardactivity where Board_Num = ?) order by activityDate desc";
    return jdbc.list(sql, new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, stringDate);
        pstmt.setInt(2, boardNum);
      }
    }, new RowMapper() {
      @Override
      public BoardActivityLog mapRow(ResultSet rs) throws SQLException {
        BoardActivityLog bal = new BoardActivityLog(rs.getInt("logNum"), rs.getInt("activityNum"), rs.getString("activity"), rs.getDate("activityDate"));
        return bal;
      }
    });
  }

  public List<String> getActivityDate(int boardNum) {
    String sql = "select DISTINCT MID(activityDate, 1, 10) as redundantDate from boardactivitylog where activityNum in (select activityNum from boardactivity where Board_Num = ?) order by redundantDate desc";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
      }
    }, new RowMapper() {
      @Override
      public String mapRow(ResultSet rs) throws SQLException {
        return rs.getString("redundantDate");
      }
    });
  }
}
