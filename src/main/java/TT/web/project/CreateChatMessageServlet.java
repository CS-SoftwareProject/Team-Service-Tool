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
import TT.domain.project.Chat;
import TT.service.support.SessionUtils;

@WebServlet("/projects/createChatMessage")
public class CreateChatMessageServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateChatMessageServlet.class);
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session=request.getSession();
    ProjectDAO projectDAO=new ProjectDAO();
    PrintWriter out=response.getWriter();
    logger.debug("response받음");
    projectDAO.addChatMessage(new Chat(request.getParameter("writer"),request.getParameter("chatMessage"), SessionUtils.getStringValue(session, "projectName"), Long.parseLong(request.getParameter("chatTime"))));
    out.print(true);
  }
}
