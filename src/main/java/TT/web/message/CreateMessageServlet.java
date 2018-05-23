package TT.web.message;

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
import TT.domain.message.Message;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/users/createMessage")
public class CreateMessageServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateMessageServlet.class);
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    MessageDAO msgDAO = new MessageDAO();
    HttpSession session = request.getSession();
    User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
    
    String sender = sessionUser.getUserId();
    String receiver = request.getParameter("receiver"); 
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");
    
    Message msg = new Message(sender, receiver, subject, content);
    msgDAO.addMessage(msg);
    
    response.sendRedirect("/users/messagelist");
  }
}
