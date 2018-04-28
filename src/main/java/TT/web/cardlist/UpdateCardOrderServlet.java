package TT.web.cardlist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.card.CardDAO;

@WebServlet("/lists/updateCardOrder")
public class UpdateCardOrderServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(UpdateListOrderServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardDAO cardDAO = new CardDAO();
    int boardNum = Integer.parseInt(request.getParameter("num"));
    int currentListOrder = Integer.parseInt(request.getParameter("currentList"));
    int updateListOrder = Integer.parseInt(request.getParameter("updateList"));
    int currentCardOrder = Integer.parseInt(request.getParameter("currentCard"));
    int updateCardOrder = Integer.parseInt(request.getParameter("updateCard"));
    logger.debug("UpdateCardOrderServlet : currentListOrder:" + currentListOrder + " updateListOrder:" + updateListOrder);
    cardDAO.updateCardOrder(boardNum, currentListOrder, updateListOrder, currentCardOrder, updateCardOrder);
  }
}
