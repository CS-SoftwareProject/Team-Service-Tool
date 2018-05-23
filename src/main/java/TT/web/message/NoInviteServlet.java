package TT.web.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.message.MessageDAO;
import TT.dao.project.ProjectDAO;
import TT.service.support.InviteMessageFactory;

@WebServlet("/message/nojoin")
public class NoInviteServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(NoInviteServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProjectDAO projectDAO = new ProjectDAO();
    MessageDAO msgDAO = new MessageDAO();
    
    String projectName = request.getParameter("projectName");
    String userId = request.getParameter("receiver");
    int msgNum = Integer.parseInt(request.getParameter("msgNum"));
    
    InviteMessageFactory factory = new InviteMessageFactory(projectName, userId);
    
    logger.debug("[para] projectName : " + projectName + " noInviter : " + userId + " msgNum : " + msgNum);
    projectDAO.removeProjectMember(userId, projectName);
    msgDAO.updateMessageContent(factory.whenNoInviteContent(), msgNum);
  }
}