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
import TT.domain.cardlist.CardList;
import TT.domain.user.User;
import TT.service.support.SessionUtils;
import TT.service.support.Activitiy.BoardActivitivationFactory;

@WebServlet("/lists/removeList")
public class DeleteCardListServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    int listOrder = Integer.parseInt(request.getParameter("listOrder"));
    String listName = request.getParameter("listName");
    HttpSession session = request.getSession();

    CardListDAO cardListDAO = new CardListDAO();
    BoardDAO boardDAO = new BoardDAO();
    cardListDAO.removeList(boardNum, listOrder);
    boardDAO.reloadBoardProgress(boardNum);
    
    User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
    BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
    CardList domainList = new CardList();
    domainList.setListName(listName);
    boardDAO.addBoardActivityLog(factory.Activity(domainList, "삭제"), boardNum);
    
    response.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}
