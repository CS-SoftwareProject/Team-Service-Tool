package TT.web.project;

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

import TT.dao.project.ProjectDAO;

@WebServlet("/projects/kickProjectUser")
public class DeleteProjectMemberServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(DeleteProjectMemberServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProjectDAO projectDAO = new ProjectDAO();
    HttpSession session = request.getSession();

    String userId = request.getParameter("userId");
    String projectName = (String) session.getAttribute("projectName");

    logger.debug("추방할 userId " + userId + "\n현재 프로젝트 =" + projectName);
    projectDAO.removeProjectMember(userId, projectName);
    
    PrintWriter out=response.getWriter();
    out.print(true);
  }
}
