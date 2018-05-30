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

@WebServlet("/cards/removecard")
public class DeleteCardServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(DeleteCardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    int num = Integer.parseInt(request.getParameter("num"));
    int listNum = Integer.parseInt(request.getParameter("listNum"));

    HttpSession session = request.getSession();
    CardDAO cardDAO = new CardDAO();
    BoardDAO boardDAO = new BoardDAO();

    try {
      User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
      int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
      Card card = cardDAO.findByCardNum(num);

      cardDAO.removeCard(num);

      BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
      boardDAO.addBoardActivityLog(factory.Activity(card, "삭제"), boardNum);
     
      boardDAO.updateBoardProgress(listNum);
      request.removeAttribute("projectCount");
      request.removeAttribute("roleCount");
      request.removeAttribute("userProjectList");
      request.removeAttribute("userAssigneeList");
      response.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
    } catch (Exception e) {
      logger.debug("RemovecardServlet error" + e);
    }

  }
}
