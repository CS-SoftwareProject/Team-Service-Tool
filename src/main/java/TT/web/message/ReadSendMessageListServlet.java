package TT.web.message;

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

import TT.dao.message.MessageDAO;
import TT.domain.message.Message;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/users/sendmessagelist")
public class ReadSendMessageListServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadMessageListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    MessageDAO msgDAO = new MessageDAO();
    List<Message> sendList = new ArrayList<Message>();

    User sessionUser = (User) SessionUtils.getObjectValue(session, "user");
    String userId = sessionUser.getUserId();
    logger.debug("userId : " + userId);

    if (userId == null) {
      response.sendRedirect("/");
      logger.debug("userId is Null ! ");
      return;
    }

    sendList = msgDAO.getSendMessageList(userId);
    logger.debug("Send Msg List : {}", sendList);

    request.setAttribute("sendList", sendList);
    request.setAttribute("isSendList", true);
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/mailbox.jsp");
    rd.forward(request, response);
  }


}
