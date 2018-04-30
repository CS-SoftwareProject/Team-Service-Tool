package TT.web.assignee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import TT.dao.assignee.AssigneeDAO;
import TT.domain.assignee.Assignee;

@WebServlet("/assignees/assigneelist")
public class ReadAssigneeListServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadAssigneeListServlet.class);
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    AssigneeDAO assigneeDAO = new AssigneeDAO();
    List<Assignee> assigneeList = new ArrayList<Assignee>();
    int cardNum = Integer.parseInt(request.getParameter("cardNum"));
    
    try {
      assigneeList = assigneeDAO.getAssigneeList(cardNum);
      Gson gson = new Gson();
      String gsonData = gson.toJson(assigneeList);
      logger.debug("[ReadAssigneeListServlet] GsonData : {}", gsonData);
      PrintWriter out = response.getWriter();
      out.print(gsonData);
    } catch (Exception e) {
      logger.debug("ReadAssigneeListServlet Error : " + e.getMessage());
    }
  }
}
