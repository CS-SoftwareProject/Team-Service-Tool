package TT.web.board;

import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet("/board/boardlist")
public class ReadBoardListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadBoardListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    session.removeAttribute("boardNum");
    
    BoardDAO boardDAO = new BoardDAO();
    
    List boardlist = new ArrayList();
    List ganttlist = new ArrayList();

    String projectName = request.getParameter("projectName");
    session.setAttribute("projectName", projectName);

    try {
      logger.debug("간트 목록 : " + ganttlist);
      request.setAttribute("isReadBoard", true);
      request.setAttribute("PBlist", boardlist = boardDAO.getBoardList(projectName));
      request.setAttribute("ganttList", ganttlist);
      request.setAttribute("projectName", projectName);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
      rd.forward(request, response);

    } catch (SQLException e) {
      logger.debug("ReadProjectListServlet error:" + e.getMessage());
    }
  }
}
