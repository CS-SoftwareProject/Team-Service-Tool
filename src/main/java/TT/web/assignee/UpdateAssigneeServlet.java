package TT.web.assignee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import TT.dao.assignee.AssigneeDAO;
import TT.dao.board.BoardDAO;
import TT.dao.card.CardDAO;
import TT.domain.assignee.Assignee;
import TT.domain.card.Card;
import TT.domain.user.User;
import TT.service.support.SessionUtils;
import TT.service.support.Activitiy.BoardActivitivationFactory;

@WebServlet("/assignees/updateAssignee")
public class UpdateAssigneeServlet extends HttpServlet{
  private static final Logger logger = LoggerFactory.getLogger(UpdateAssigneeServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AssigneeDAO assigneeDAO = new AssigneeDAO();
      Assignee assignee = new Assignee();
      HttpSession session = request.getSession();
      
      int assigneeNum = Integer.parseInt(request.getParameter("assigneeNum"));
      int memberNum = Integer.parseInt(request.getParameter("memberNum"));
      int roleNum = Integer.parseInt(request.getParameter("roleNum"));
      int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
      logger.debug("UpdateAssigneeServlet Para :\nassigneeNum : " + assigneeNum + "\nmemberNum : " + memberNum + "\nroleNum : " + roleNum + "\nboardNum : " + boardNum);
     
      try {
        Assignee oldAssignee = assigneeDAO.findByAssigneeNum(SessionUtils.getStringValue(session, "projectName"));
        
        assigneeDAO.updateAssignee(assigneeNum, memberNum, roleNum);
        assignee = assigneeDAO.getAsiggnee(memberNum, roleNum, assigneeNum);
        
        CardDAO cardDAO = new CardDAO();
        BoardDAO boardDAO = new BoardDAO();
        User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
        BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
        Card card = cardDAO.findByCardNum(oldAssignee.getCardNum());
        
        logger.debug("담당자 추가 Acitivity ?" + factory.updateActivityInCard(card.getSubject(), oldAssignee, assignee));
        
        boardDAO.addBoardActivityLog(factory.updateActivityInCard(card.getSubject(), oldAssignee, assignee), boardNum);
        
        Gson gson = new Gson();
        String gsonData = gson.toJson(assignee);
        logger.debug("updated Assginee : " + gsonData);
        PrintWriter out = response.getWriter();
        out.println(gsonData);
      } catch (Exception e) {
        logger.debug("UpdateAssigneeServlet Error : " + e.getMessage());
      }
  }
}