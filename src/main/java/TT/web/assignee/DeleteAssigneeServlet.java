package TT.web.assignee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.assignee.AssigneeDAO;

@WebServlet("/assignees/deleteAssignee")
public class DeleteAssigneeServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(DeleteAssigneeServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    AssigneeDAO assigneeDAO = new AssigneeDAO();
    int assigneeNum = Integer.parseInt(request.getParameter("assigneeNum"));
    logger.debug("DeleteAssigneeServler Para : " + assigneeNum);
    try {
      assigneeDAO.removeAssignee(assigneeNum);
    } catch (Exception e) {
      logger.debug("DeleteAssigneeServlet Error : " + e.getMessage());
    }
  }
}
