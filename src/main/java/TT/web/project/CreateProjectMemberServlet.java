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

import TT.dao.message.MessageDAO;
import TT.dao.project.ProjectDAO;
import TT.domain.message.Message;
import TT.domain.user.User;
import TT.service.support.InviteMessageFactory;
import TT.service.support.SessionUtils;

@WebServlet("/projects/inviteUser")
public class CreateProjectMemberServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateProjectMemberServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    ProjectDAO projectDAO = new ProjectDAO();
    MessageDAO msgDAO = new MessageDAO();

    String projectName = (String) session.getAttribute("projectName");
    User user = (User) SessionUtils.getObjectValue(session, "user");
    String inviter = user.getUserId();
    String receiver = request.getParameter("userId");
    final int power = 0;
    
    InviteMessageFactory factory = new InviteMessageFactory(projectName, receiver);

    Message message = new Message(inviter, receiver, factory.getSubject(), factory.getContent());
    try {
      projectDAO.InviteUser(receiver, projectName, power);
      msgDAO.addMessage(message);
    } catch (Exception e) {
      logger.debug("Search fail : " + e);
    }
  }
}
