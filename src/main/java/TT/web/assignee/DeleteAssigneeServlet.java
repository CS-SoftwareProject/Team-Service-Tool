package TT.web.assignee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.assignee.AssigneeDAO;
import TT.dao.board.BoardDAO;
import TT.dao.card.CardDAO;
import TT.domain.assignee.Assignee;
import TT.domain.card.Card;
import TT.domain.user.User;
import TT.service.support.SessionUtils;
import TT.service.support.Activitiy.BoardActivitivationFactory;

@WebServlet("/assignees/deleteAssignee")
public class DeleteAssigneeServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(DeleteAssigneeServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    AssigneeDAO assigneeDAO = new AssigneeDAO();
    
    int assigneeNum = Integer.parseInt(request.getParameter("assigneeNum"));
    logger.debug("DeleteAssigneeServler Para : " + assigneeNum);
    
    try {
      Assignee assignee = assigneeDAO.findByAssigneeNum(SessionUtils.getStringValue(session, "projectName"));
      assigneeDAO.removeAssignee(assigneeNum);
      
      CardDAO cardDAO = new CardDAO();
      BoardDAO boardDAO = new BoardDAO();
      int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
      Card card = cardDAO.findByCardNum(assignee.getCardNum());
      User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
      BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
      logger.debug("담당자 추가 Acitivity ?" + factory.activityInCard(card.getSubject(), assignee, "삭제"));
      
      boardDAO.addBoardActivityLog(factory.activityInCard(card.getSubject(), assignee, "삭제"), boardNum);
      
    } catch (Exception e) {
      logger.debug("DeleteAssigneeServlet Error : " + e.getMessage());
    }
  }
}
