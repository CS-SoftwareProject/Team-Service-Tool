package TT.web.cardlist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import TT.dao.board.BoardDAO;
import TT.dao.cardlist.CardListDAO;

@WebServlet("/lists/removeList")
public class DeleteCardListServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    int listOrder = Integer.parseInt(request.getParameter("listOrder"));

    HttpSession session = request.getSession();

    CardListDAO cardListDAO = new CardListDAO();
    BoardDAO boardDAO = new BoardDAO();
    cardListDAO.removeList(boardNum, listOrder);
    boardDAO.reloadBoardProgress(boardNum);
    response.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}
