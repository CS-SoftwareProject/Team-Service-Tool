package TT.web.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import TT.dao.message.MessageDAO;
import TT.dao.user.UserDAO;
import TT.domain.message.Message;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/message/notread")
public class ReadNoReadMessageListServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadNoReadMessageListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    MessageDAO msgDAO = new MessageDAO();
    UserDAO userDAO = new UserDAO();
    HttpSession session = request.getSession();
    User sessionUser = (User) SessionUtils.getObjectValue(session, "user");
    Gson gson = new Gson();
    JsonArray arr = new JsonArray();

    String userId = sessionUser.getUserId();
    int notRead = 0;
    List<Message> list = new ArrayList<Message>();
    
    notRead = msgDAO.getNotReadMessage(userId);
    list = msgDAO.getLastMessageList(sessionUser.getUserId());
    
    for (int i = 0; i < list.size(); i++) {
      JsonObject obj = new JsonObject();
      obj.add("message", gson.toJsonTree(list.get(i)));
      obj.add("image", gson.toJsonTree(userDAO.getImageByUserId(list.get(i).getSender())));
      obj.add("notRead", gson.toJsonTree(notRead));
      arr.add(obj);
    }

    String gsonData = gson.toJson(arr);
    PrintWriter out = response.getWriter();
    out.print(gsonData);
  }
}
