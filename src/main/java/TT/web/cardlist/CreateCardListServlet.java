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

@WebServlet("/lists/addList")
public class CreateCardListServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardList cardList=new CardList(Integer.parseInt(request.getParameter("boardNum")),request.getParameter("listName"),Integer.parseInt(request.getParameter("listOrder")));
    CardListDAO cardListDAO = new CardListDAO();
    BoardDAO boardDAO = new BoardDAO();
    cardListDAO.addList(cardList);
    
    HttpSession session = request.getSession();
    User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
    int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
    BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
    boardDAO.addBoardActivityLog(factory.Activity(cardList, "추가"), boardNum);
    
    response.sendRedirect("/lists/cardlist?boardNum=" + cardList.getBoardNum());
  }
}
