package TT.web.cardlist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import TT.dao.cardlist.CardListDAO;

@WebServlet("/lists/updateListName")
public class UpdateListNameServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardListDAO cardListDAO=new CardListDAO();
    cardListDAO.updateListName(Integer.parseInt(request.getParameter("num")), request.getParameter("name"));
  }
}
