package TT.web.card;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import TT.dao.board.BoardDAO;
import TT.dao.card.CardDAO;
import TT.domain.card.Card;

@WebServlet("/cards/cuProgress")
public class UpdateProgressServlet extends HttpServlet{
  private static final Logger logger = LoggerFactory.getLogger(UpdateProgressServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BoardDAO boardDAO = new BoardDAO();
    CardDAO cardDAO = new CardDAO();
    Card card = new Card();
    int cardNum = Integer.parseInt(request.getParameter("cardNum"));
    int progress = Integer.parseInt(request.getParameter("progress"));
    int cardListNum = Integer.parseInt(request.getParameter("cardListNum"));
    logger.debug("[Update Progress Servlet] cardListNum : " + cardListNum);
    
    try {
      cardDAO.updateCardProgress(progress, cardNum);
      card.setProgress(progress);
      card.setCardNum(cardNum);
      boardDAO.updateBoardProgress(cardListNum);
      Gson gson = new Gson();
      String gsonData = gson.toJson(card);
      logger.debug("UpdateProgressServlet : " + gsonData);
      PrintWriter out = response.getWriter();
      out.print(gsonData);
    } catch (Exception e) {
      logger.debug("CreateDueDateServlet Error : " + e.getMessage());
    } 
  }
}

