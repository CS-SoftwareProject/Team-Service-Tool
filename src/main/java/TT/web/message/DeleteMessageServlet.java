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

@WebServlet("/users/removeMessage")
public class DeleteMessageServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(DeleteMessageServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    MessageDAO msgDAO = new MessageDAO();
    
    int msgNum = Integer.parseInt(request.getParameter("msgNum"));
    logger.debug("delete msgNum : " + msgNum);
    msgDAO.removeMessage(msgNum);
    response.sendRedirect("/users/messagelist");
  }
}
