package TT.web.message;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.message.MessageDAO;
import TT.domain.message.Message;

@WebServlet("/users/readmail")
public class ReadMessageServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadMessageServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    MessageDAO msgDAO = new MessageDAO();
    Message msg = new Message();
    int msgNum = Integer.parseInt(request.getParameter("msgNum"));
    logger.debug("[para] msgNum : " + msgNum);
    
    msgDAO.isRead(msgNum);
    msg = msgDAO.getByMessageNum(msgNum);
    logger.debug("[obj] msg : {}", msg);
    request.setAttribute("msg", msg);
    
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/readmail.jsp");
    rd.forward(request, response);
  }
}
