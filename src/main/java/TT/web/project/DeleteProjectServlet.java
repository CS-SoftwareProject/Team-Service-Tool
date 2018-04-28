package TT.web.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.project.ProjectDAO;

@WebServlet("/project/deleteProject")
public class DeleteProjectServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(DeleteProjectServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    ProjectDAO projectDAO = new ProjectDAO();

    String projectName = request.getParameter("projectName");

    logger.debug("delete project name = " + projectName);
    try {
      projectDAO.removeProject(projectName);
      response.sendRedirect("/project/projectlist");
    } catch (Exception e) {
      logger.debug("Delete fail : " + e);
    }

  }

}
