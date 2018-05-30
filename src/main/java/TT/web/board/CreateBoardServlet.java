package TT.web.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.board.BoardDAO;
import TT.domain.board.Board;

@WebServlet("/board/createBoard")
public class CreateBoardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateBoardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Board board = new Board();
    BoardDAO boardDAO = new BoardDAO();

    HttpSession session = request.getSession();

    String projectName = (String) session.getAttribute("projectName");
    String boardName = request.getParameter("boardName");
    String boardInfo = request.getParameter("boardInfo");
    logger.debug("[Create Board] boardInfo : " + boardInfo);
    
    board.setProjectName(projectName);
    board.setBoardName(boardName);
    board.setBoardInfo(boardInfo);

    projectName = URLEncoder.encode(projectName, "UTF-8");

    try {
      int thisBoardNum = boardDAO.addBoard(board);
      boardDAO.addBoardActivity(thisBoardNum);
      String activity = "<a href='#'>'" + boardName + "'</a> 보드가 <a href='#'><b>생성되었습니다.</b></a>";
      boardDAO.addBoardActivityLog(activity, thisBoardNum);
      
      response.sendRedirect("/board/boardlist?projectName=" + projectName);
    } catch (Exception e) {
      logger.debug("Board create fail : " + e);
    }
  }
}
