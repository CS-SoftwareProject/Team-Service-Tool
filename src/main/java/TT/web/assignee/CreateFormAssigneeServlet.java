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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import TT.dao.project.ProjectDAO;
import TT.dao.role.RoleDAO;
import TT.domain.project.ProjectMember;
import TT.domain.role.Role;
import TT.service.support.SessionUtils;

@WebServlet("/assignees/CreateFormAssignee")
public class CreateFormAssigneeServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateFormAssigneeServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    ProjectDAO projectDAO = new ProjectDAO();
    RoleDAO roleDAO = new RoleDAO();
    List<ProjectMember> memberList = new ArrayList<ProjectMember>();
    List<Role> roleList = new ArrayList<Role>();
    
    String projectName = SessionUtils.getStringValue(session, "projectName");
    int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
    
    try {
      logger.debug("CreateFormAssigneeServlet Session [projectName] = " + projectName);
      
      memberList = projectDAO.getProjectMemberList(projectName);
      roleList = roleDAO.getRoleList(boardNum);
      
      JsonArray arr = new JsonArray();
      
      Gson gson = new Gson();
      
      arr.add(gson.toJson(memberList));
      arr.add(gson.toJson(roleList));
      logger.debug("CreateFormAssigneeServet gsonData = " + arr);
      PrintWriter out = response.getWriter();
      out.print(arr);
      
    } catch (Exception e) {
      logger.debug("CreateFormAssigneeServlet Error : " + e.getMessage());
    }
  }
}
