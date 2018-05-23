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
import TT.domain.assignee.Assignee;
import TT.service.support.SessionUtils;

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
        assigneeDAO.updateAssignee(assigneeNum, memberNum, roleNum);
        logger.debug("여기 통과함");
        assignee = assigneeDAO.getAsiggnee(memberNum, roleNum, assigneeNum);
        logger.debug("뭐야 : {}", assignee);
        Gson gson = new Gson();
        String gsonData = gson.toJson(assignee);
        logger.debug("update어싸이니 : " + gsonData);
        PrintWriter out = response.getWriter();
        out.println(gsonData);
      } catch (Exception e) {
        logger.debug("UpdateAssigneeServlet Error : " + e.getMessage());
      }
  }
}
