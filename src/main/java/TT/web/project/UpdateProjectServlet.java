package TT.web.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.project.ProjectDAO;

@WebServlet("/project/updateProject")
public class UpdateProjectServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(UpdateProjectServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProjectDAO projectDAO = new ProjectDAO();
    String preProjectName = request.getParameter("preProjectName");
    String newProjectName = request.getParameter("newProjectName");

    try {
      projectDAO.updateProject(newProjectName, preProjectName);
      response.sendRedirect("/project/projectlist");

    } catch (Exception e) {
      logger.debug("Update Project fail = " + e);
    }

  }
}
