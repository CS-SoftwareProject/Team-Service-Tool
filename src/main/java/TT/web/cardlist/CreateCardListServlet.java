package TT.web.cardlist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import TT.dao.cardlist.CardListDAO;
import TT.domain.cardlist.CardList;

@WebServlet("/lists/addList")
public class CreateCardListServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardList cardList=new CardList(Integer.parseInt(request.getParameter("boardNum")),request.getParameter("listName"),Integer.parseInt(request.getParameter("listOrder")));
    CardListDAO cardListDAO = new CardListDAO();
    cardListDAO.addList(cardList);

    response.sendRedirect("/lists/cardlist?boardNum=" + cardList.getBoardNum());
  }
}
