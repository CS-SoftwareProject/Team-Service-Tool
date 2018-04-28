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

import TT.dao.project.ProjectDAO;
import TT.domain.project.Project;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/project/projectlist")
public class ReadProjectListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadProjectListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    List<Project> projectlist = new ArrayList<Project>();
    ProjectDAO projectDAO = new ProjectDAO();

    session.removeAttribute("projectName");

    User sessionUser=(User)SessionUtils.getObjectValue(session, "user");
    String userId = sessionUser.getUserId();
    projectlist = projectDAO.getProjectList(userId);
    request.setAttribute("isReadProject", true);
    request.setAttribute("PBlist", projectlist);

    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
    rd.forward(request, response);
  }
}
