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

import TT.dao.board.BoardDAO;
import TT.dao.card.CardDAO;
import TT.domain.card.Card;
import TT.domain.user.User;
import TT.service.support.SessionUtils;
import TT.service.support.Activitiy.BoardActivitivationFactory;

@WebServlet("/cards/updatecard")
public class UpdateCardServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(UpdateCardServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    User sessionUser = (User) session.getAttribute("user");

    int cardNum = Integer.parseInt(request.getParameter("num"));
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");
    String userId = sessionUser.getUserId();

    Card card = new Card();
    card.setUserId(userId);
    card.setCardNum(cardNum);
    card.setSubject(subject);
    card.setContent(content);

    CardDAO cardDAO = new CardDAO();
    BoardDAO boardDAO = new BoardDAO();
    try {
      Card oldCard = cardDAO.findByCardNum(cardNum);
      cardDAO.updateCard(card);

      int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
      BoardActivitivationFactory factory = new BoardActivitivationFactory(userId);
      boardDAO.addBoardActivityLog(factory.Activity(oldCard, "수정"), boardNum);;
      
    } catch (Exception e) {
      logger.debug("updatecard Servlet error" + e);
    }
    response.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}
