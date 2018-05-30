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

@WebServlet("/lists/updateListName")
public class UpdateListNameServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BoardDAO boardDAO = new BoardDAO();
    CardListDAO cardListDAO = new CardListDAO();
    int listNum = Integer.parseInt(request.getParameter("num"));
    
    CardList oldCardList = cardListDAO.findByListNum(listNum);
    cardListDAO.updateListName(listNum, request.getParameter("name"));
    
    HttpSession session = request.getSession();
    User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
    int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
    BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
    boardDAO.addBoardActivityLog(factory.updateActivity(oldCardList, oldCardList.getListName(), request.getParameter("name")), boardNum);
  }
}
