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

import com.google.gson.JsonArray;

import TT.dao.assignee.AssigneeDAO;
import TT.service.support.SessionUtils;

@WebServlet("/assignees/createAssignee")
public class CreateAssigneeServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateAssigneeServlet.class);
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    AssigneeDAO assigneeDAO = new AssigneeDAO();
    
    String assigneeMember = request.getParameter("assigneeMember");
    String roleName = request.getParameter("roleName");
    int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
    int cardNum = Integer.parseInt(request.getParameter("cardNum"));
    int assigneeNum;
    logger.debug("CreateAssigneeServlet Parameters [assignee : " + assigneeMember + ", " + "role : " + roleName + ", " + "cardNum : " + cardNum + "]");
    
    try {
      assigneeNum = assigneeDAO.addAssignee(assigneeMember, roleName, boardNum, cardNum);
      JsonArray arr = new JsonArray();
      arr.add(assigneeNum);
      arr.add(assigneeMember);
      arr.add(roleName);
      arr.add(cardNum);
      logger.debug("CreateAssigneeServlet JsonArray : {}", arr);
      PrintWriter out = response.getWriter();
      out.print(arr);
    } catch (Exception e) {
      logger.debug("CreateAssigneeServlet Error : " + e.getMessage());
    }
  }
}
