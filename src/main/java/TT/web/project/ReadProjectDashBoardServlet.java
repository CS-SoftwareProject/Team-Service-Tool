package TT.web.project;

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
import TT.dao.card.CardDAO;
import TT.dao.project.ProjectDAO;
import TT.domain.card.Card;
import TT.domain.project.ProjectDashBoard;
import TT.service.support.SessionUtils;

@WebServlet("/project/projectDashBoard")
public class ReadProjectDashBoardServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadProjectDashBoardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    String projectName = SessionUtils.getStringValue(session, "projectName");
    
    ProjectDAO projectDAO = new ProjectDAO();
    CardDAO cardDAO = new CardDAO();
    BoardDAO boardDAO = new BoardDAO();
    ProjectDashBoard projectDash = new ProjectDashBoard();
    
    List<Card> lastCardList = new ArrayList<Card>();
    
    projectDash.setProjectName(projectName);
    try {
      projectDash.setBoards(boardDAO.getBoardList(projectName));
      projectDash.setProjectProgress(projectDAO.getProjectProgress(projectName));
      projectDash.setProjectMembers(projectDAO.getProjectDashMember(projectName));
      lastCardList = cardDAO.getLastCardList(projectName);
      lastCardList = cardDAO.setBoardName(lastCardList);
      projectDash.setLastCards(lastCardList);
      
      logger.debug("[DashBoard] Project Member : " + projectDash.getProjectMembers());
      logger.debug("[DashBoard] Last Card : " + projectDash.getLastCards());
      logger.debug("[DashBoard] Board List : " + projectDash.getBoards());
      
      request.setAttribute("projectDash", projectDash);
      request.setAttribute("isReadBoard", true);
      RequestDispatcher rd = request.getRequestDispatcher("/projectDash.jsp");
      rd.forward(request, response);
    } catch (Exception e) {
      logger.debug("ReadProjectDashBoard Fail : " + e.getMessage());
    }
  }
}
