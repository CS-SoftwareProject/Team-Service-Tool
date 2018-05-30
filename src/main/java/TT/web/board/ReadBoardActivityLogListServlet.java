package TT.web.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.board.BoardDAO;
import TT.domain.board.Board;
import TT.domain.board.BoardActivityLog;

@WebServlet("/boards/loglist")
public class ReadBoardActivityLogListServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadBoardActivityLogListServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BoardDAO boardDAO = new BoardDAO();
    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    Board board = boardDAO.findByBoardNum(boardNum);
    
    List<String> dateList = boardDAO.getActivityDate(boardNum);
    List<List<BoardActivityLog>> balList = new ArrayList<List<BoardActivityLog>>();
    logger.debug("dateList {} ", dateList);
    
    for (int i = 0; i < dateList.size(); i++) {
      balList.add(boardDAO.findByStringTypeDate(dateList.get(i), boardNum));
    }
    logger.debug("balList[0] {} ", balList);
    
    request.setAttribute("isReadCard", true);
    request.setAttribute("isReadBoard", true);
    request.setAttribute("board", board);
    request.setAttribute("dateList", dateList);
    request.setAttribute("balList", balList);
    
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/boardlog.jsp");
    rd.forward(request, response);
  }
}