package TT.web.user;

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
import com.google.gson.JsonArray;

import TT.dao.project.ProjectDAO;
import TT.dao.user.UserDAO;
import TT.domain.project.Project;
import TT.domain.user.User;

@WebServlet("/users/readProfile")
public class ReadUserProfileServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadUserProfileServlet.class);
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserDAO userDAO = new UserDAO();
    ProjectDAO projectDAO = new ProjectDAO();
    List<Project> userProjectList = new ArrayList<Project>();
    User targetUser = new User();
    
    String userId = request.getParameter("userId");
    logger.debug("ReadUserProfile Para : " + userId);
    
    try {
      userProjectList = projectDAO.getProjectList(userId);
      logger.debug("ReadUserProfile userProject : " + userProjectList);
      targetUser = userDAO.getByUserId(userId);
      logger.debug("ReadUserProfile user : " + targetUser);
      Gson gson = new Gson();
      JsonArray arr = new JsonArray();
      arr.add(gson.toJson(targetUser));
      arr.add(gson.toJson(userProjectList));
      PrintWriter out = response.getWriter();
      out.print(arr);
    } catch (Exception e) {
      logger.debug("ReadUserProfile fail : " + e.getMessage());
    }
  }
}
