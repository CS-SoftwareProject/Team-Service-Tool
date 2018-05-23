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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import TT.dao.message.MessageDAO;
import TT.domain.user.User;

@WebServlet("/message/realtimelist")
public class ReadRealTimeUserListServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadRealTimeUserListServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    MessageDAO msgDAO = new MessageDAO();
    List<User> list = new ArrayList<User>();
    
    String keyword = request.getParameter("keyword");
    logger.debug("실시간 검색유저 keyword : " + keyword);
    list = msgDAO.getRealTimeUserList(keyword);
    logger.debug("실시간 검색유저 list : " + list);
    
    Gson gson = new Gson();
    String gsonData = gson.toJson(list);
    PrintWriter out = response.getWriter();
    out.print(gsonData);
  }
}
