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

@WebServlet("/board/deleteBoard")
public class DeleteBoardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(DeleteBoardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BoardDAO boardDAO = new BoardDAO();
    HttpSession session = request.getSession();

    String projectName = (String) session.getAttribute("projectName");
    String boardName = request.getParameter("boardName");

    logger.debug("delete board name = " + boardName);
    projectName = URLEncoder.encode(projectName, "UTF-8");
    try {
      boardDAO.removeBoard(boardName);
      response.sendRedirect("/board/boardlist?projectName=" + projectName);
    } catch (Exception e) {
      logger.debug("Delete fail : " + e);
    }

  }

}
