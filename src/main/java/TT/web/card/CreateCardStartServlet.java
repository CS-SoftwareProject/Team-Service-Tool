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

import TT.dao.card.CardDAO;
import TT.domain.card.Card;

@WebServlet("/cards/CreateDueDate")
public class CreateCardStartServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateCardStartServlet.class);
 
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardDAO cardDAO = new CardDAO();
    Card card = new Card();
    int cardNum = Integer.parseInt(request.getParameter("cardNum"));
    long start = Long.parseLong(request.getParameter("start"));
    int duration = Integer.parseInt(request.getParameter("duration"));
    
    try {
      cardDAO.updateCardStart(start, duration ,cardNum);
      card = new Card(cardNum, start);
      
      Gson gson = new Gson();
      String gsonData = gson.toJson(card);
      logger.debug("CreateDueDateServlet : " + gsonData);
      PrintWriter out = response.getWriter();
      out.print(gsonData);
        
    } catch (Exception e) {
      logger.debug("CreateDueDateServlet Error : " + e.getMessage());
    } 
  }
}
