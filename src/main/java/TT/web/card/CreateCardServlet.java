package TT.web.card;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.card.CardDAO;
import TT.domain.card.Card;

@WebServlet("/cards/createcard")
public class CreateCardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateCardServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardDAO cardDAO = new CardDAO();
    HttpSession session = request.getSession();
    // Card
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");
    String userId = request.getParameter("userId");
    int listNum = Integer.parseInt(request.getParameter("listNum"));
    int cardOrder = Integer.parseInt(request.getParameter("cardOrder"));
    int progress = Integer.parseInt(request.getParameter("progress"));
    
    Card card = new Card(userId, subject, content, listNum, cardOrder ,progress);
    logger.debug("CreateCardServlet 에서 받은 card객체 : " + card.toString());
    try {
      cardDAO.addCard(card);
    } catch (Exception e) {
      logger.debug("CreateCardServlet error:" + e.getMessage());
    }
    response.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}