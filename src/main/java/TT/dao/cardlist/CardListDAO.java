package TT.dao.cardlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.card.CardDAO;
import TT.domain.cardlist.CardList;
import TT.service.support.jdbc.JdbcTemplate;
import TT.service.support.jdbc.PreparedStatementSetter;
import TT.service.support.jdbc.RowMapper;
import TT.service.support.jdbc.SelectAndUpdateSetter;

public class CardListDAO {
  public static Logger logger = LoggerFactory.getLogger(CardList.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public List<CardList> getLists(int boardNum) {
    CardDAO cardDAO = new CardDAO();

    String sql = "select*from lists where Board_Num=? order by List_Order";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
      }
    }, new RowMapper() {
      @Override
      public CardList mapRow(ResultSet rs) throws SQLException {
        CardList cardList = new CardList();
        cardList.setListNum(rs.getInt("List_Num"));
        cardList.setBoardNum(boardNum);
        cardList.setListName(rs.getString("List_Name"));
        cardList.setListOrder(rs.getInt("List_Order"));
        cardList.setCards(cardDAO.getCards(rs.getInt("List_Num")));
        return cardList;
      }
    });
  }

  public void addList(CardList list) {
    String sql = "insert into lists(Board_Num,List_Name,List_Order) values(?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, list.getBoardNum());
        pstmt.setString(2, list.getListName());
        pstmt.setInt(3, list.getListOrder());
      }
    });
  }

  public void removeList(int boardNum, int listOrder) {
    String sql = "delete from lists where Board_Num=? && List_Order=?";
    // delete list query
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
        pstmt.setInt(2, listOrder);
      }
    });

    // select and update listOrder
    sql = "select List_Num from lists where Board_Num=? && List_Order>? order by List_Order asc";
    String sql2 = "update lists set List_Order=? where List_Num=?";
    jdbc.selectAndUpdate(sql, sql2, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
        pstmt.setInt(2, listOrder);
      }
    }, new SelectAndUpdateSetter() {
      int changeOrder = listOrder;

      @Override
      public void setParametersBySelect(PreparedStatement pstmt, ResultSet rs) throws SQLException {
        pstmt.setInt(1, changeOrder++);
        pstmt.setInt(2, rs.getInt("List_Num"));
      }
    });
  }

  public void updateListName(int listNum, String listName) {
    String sql = "update lists set List_Name=? where List_Num=?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, listName);
        pstmt.setInt(2, listNum);
      }
    });
  }

  // listNum : 기존 list의 Num , changeOrder : 인덱스 변경 시 사용할 순서 번호
  public void updateListOrder(int boardNum, int currentListOrder, int changeListOrder) {
    String sql, sql2;

    CardList currentList = new CardList(boardNum, currentListOrder);
    final int listNum = getListNum(currentList.getBoardNum(), currentList.getListOrder());

    // 1. 변경된 위치 < 기존 리스트 순서 : 기존 리스트 순서 ~ 바뀐 리스트 전 리스트까지 sorting
    if (currentListOrder > changeListOrder) {
      sql = "select List_Num from lists where Board_Num=? && List_Order>=? && List_Order<?  order by List_Order asc";
      sql2 = "update lists set List_Order = ? where List_Num=?";
      jdbc.selectAndUpdate(sql, sql2, new PreparedStatementSetter() {
        @Override
        public void setParameters(PreparedStatement pstmt) throws SQLException {
          pstmt.setInt(1, boardNum);
          pstmt.setInt(2, changeListOrder);
          pstmt.setInt(3, currentListOrder);
        }
      }, new SelectAndUpdateSetter() {
        int changeOrder = changeListOrder + 1;

        @Override
        public void setParametersBySelect(PreparedStatement pstmt, ResultSet rs) throws SQLException {
          pstmt.setInt(1, changeOrder++);
          pstmt.setInt(2, rs.getInt("List_Num"));
        }
      });
    }

    // 2. 기존 리스트 순서 < 바뀐 리스트 순서 : 기존 리스트 옆 ~ 바뀐 리스트까지 sorting
    else {
      sql = "select List_Num from lists where Board_Num=? && List_Order>? && List_Order<=? order by List_Order asc";
      sql2 = "update lists set List_Order = ? where List_Num=?";
      jdbc.selectAndUpdate(sql, sql2, new PreparedStatementSetter() {
        @Override
        public void setParameters(PreparedStatement pstmt) throws SQLException {
          pstmt.setInt(1, boardNum);
          pstmt.setInt(2, currentListOrder);
          pstmt.setInt(3, changeListOrder);
        }
      }, new SelectAndUpdateSetter() {
        int changeOrder = currentListOrder;

        @Override
        public void setParametersBySelect(PreparedStatement pstmt, ResultSet rs) throws SQLException {
          pstmt.setInt(1, changeOrder++);
          pstmt.setInt(2, rs.getInt("List_Num"));
        }
      });
    }

    // if drag list action
    sql = "update lists set List_Order = ? where List_Num = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, changeListOrder);
        pstmt.setInt(2, listNum);
      }
    });
  }

  public CardList findByListNum(int listNum) {
    String sql = "select * from lists where List_Num=?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, listNum);
      }
    }, new RowMapper() {
      @Override
      public CardList mapRow(ResultSet rs) throws SQLException {
        while (rs.next())
          return new CardList(rs.getInt("List_Num"), rs.getInt("Board_Num"), rs.getString("List_Name"), rs.getInt("List_Order"));
        return null;
      }
    });
  }

  public int getListNum(int boardNum, int listOrder) {
    String sql = "select List_Num from lists where Board_Num=? && List_Order=?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
        pstmt.setInt(2, listOrder);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        while (rs.next())
          return rs.getInt("List_Num");
        return 0;
      }
    });
  }

}
