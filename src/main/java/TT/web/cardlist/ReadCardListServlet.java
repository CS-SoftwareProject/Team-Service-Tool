package TT.web.cardlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.board.BoardDAO;
import TT.dao.cardlist.CardListDAO;
import TT.domain.board.Board;
import TT.service.support.SessionUtils;

@WebServlet("/lists/cardlist")
public class ReadCardListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadCardListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
   
    BoardDAO boardDAO = new BoardDAO();
    CardListDAO cardListDAO = new CardListDAO();
    Board board = new Board();
    List cardlist = new ArrayList();
    String projectName = SessionUtils.getStringValue(session, "projectName");
    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    
    try {
      session.getAttribute("projectName");
      session.setAttribute("boardNum", boardNum);
      
      board = boardDAO.getByBoardNum(boardNum);
      cardlist = cardListDAO.getLists(boardNum);
      
      request.setAttribute("board", board);
      request.setAttribute("lists", cardlist);
      request.setAttribute("projectName", projectName);
      request.setAttribute("isReadBoard", true);
      request.setAttribute("isReadCard", true);
      logger.debug("ReadCardListServlet db에서 가져온 lists:" + cardlist);
      
      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/card_list.jsp");
      rd.forward(request, response);
    } catch (Exception e) {
    }
  }
}
