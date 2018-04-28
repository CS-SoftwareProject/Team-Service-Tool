package TT.web.card;

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

import com.google.gson.Gson;

import TT.domain.card.Card;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/cards/createcardForm")
public class CreateFormCardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateFormCardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    User sessionUser=(User)SessionUtils.getObjectValue(session, "user");
    String userId = sessionUser.getUserId();
    PrintWriter out = response.getWriter();

    int listNum = Integer.parseInt(request.getParameter("listNum"));
    int cardOrder = Integer.parseInt(request.getParameter("cardOrder"));

    try {
      Gson gson = new Gson();
      String jsonData = gson.toJson(new Card(listNum, cardOrder));
      out.print(jsonData);
    } catch (Exception e) {
      logger.debug("CreateFormCardServlet error:" + e.getMessage());
    } finally {
      out.close();
    }
  }
}
