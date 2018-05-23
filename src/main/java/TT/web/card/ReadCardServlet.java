package TT.web.card;

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

import TT.dao.assignee.AssigneeDAO;
import TT.dao.card.CardDAO;
import TT.domain.card.Card;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/cards/viewcard")
public class ReadCardServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ReadCardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    User sessionUser = (User) SessionUtils.getObjectValue(session, "user");
    
    CardDAO cardDAO = new CardDAO();
    Card card = new Card();
    AssigneeDAO assigneeDAO = new AssigneeDAO();

    List assigneeList = new ArrayList();
    int cardNum = Integer.parseInt(request.getParameter("cardNum"));

    try {
      card = cardDAO.viewCard(cardNum);
      card.setAssignees(assigneeDAO.getAssigneeList(cardNum));
      logger.debug("카드의 Assignee : {}", card.getAssignees());
      logger.debug("카드 : {}", card);
      if (card == null) {
        logger.debug("card View null");
      }
      Gson gson = new Gson();
      String gsonData = gson.toJson(card);
      out.print(gsonData);
    } catch (Exception e) {
      logger.debug("cardviewServlet error : " + e);
    } finally {
      out.close();
    }
  }
}
