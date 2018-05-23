package TT.web.user;

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

import TT.dao.project.ProjectDAO;
import TT.dao.user.UserDAO;
import TT.domain.project.Project;
import TT.domain.user.User;
import TT.domain.user.UserDashBoard;
import TT.service.support.SessionUtils;

@WebServlet("/users/userDashBoard")
public class ReadUserDashBoardServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadUserDashBoardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    ProjectDAO projectDAO = new ProjectDAO();
    UserDAO userDAO = new UserDAO();

    List<Project> userProjectList = new ArrayList<Project>();
    List<UserDashBoard> userAssigneeList = new ArrayList<UserDashBoard>();

    User sessionUser = (User) SessionUtils.getObjectValue(session, "user");
    String userId = sessionUser.getUserId();
    int thisUserNum = projectDAO.getProjectNumByUserId(userId);
    int projectCount = 0;
    int roleCount = 0;
    logger.debug("getUserId : " + userId);
    logger.debug("PM_Num : " + thisUserNum);

    try {
      userProjectList = projectDAO.getProjectList(userId);
      logger.debug("ProjectList : " + userProjectList);
      projectCount = userProjectList.size();
      logger.debug("Project Counter : " + projectCount);

      userAssigneeList = userDAO.getUserDashBoardData(thisUserNum);
      logger.debug("user AssigneeList : " + userAssigneeList);
      
      if (userAssigneeList != null) {
        roleCount = userAssigneeList.size();
      }
      
      logger.debug("ReadUserDashBoard user Role Counter : " + roleCount);

      request.setAttribute("userId", userId);
      request.setAttribute("projectCount", projectCount);
      request.setAttribute("roleCount", roleCount);
      request.setAttribute("userProjectList", userProjectList);
      request.setAttribute("userAssigneeList", userAssigneeList);

      RequestDispatcher rd = request.getRequestDispatcher("/userDash.jsp");
      rd.forward(request, response);
    } catch (Exception e) {
      logger.debug("ReadUserDashBoard Fail : " + e);
    }
  }
}
