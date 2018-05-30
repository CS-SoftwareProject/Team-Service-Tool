package TT.web.role;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import TT.dao.board.BoardDAO;
import TT.dao.role.RoleDAO;
import TT.domain.role.Role;
import TT.domain.user.User;
import TT.service.support.SessionUtils;
import TT.service.support.Activitiy.BoardActivitivationFactory;

@WebServlet("/roles/deleteRole")
public class DeleteRoleServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(DeleteRoleServlet.class);
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    RoleDAO roleDAO = new RoleDAO();
    int roleNum = Integer.parseInt(request.getParameter("roleNum"));
    logger.debug("Get Para = {}", request.getParameter("roleNum"));
    
    try {
      Role oldRole = roleDAO.findByRoleNum(roleNum);
      roleDAO.deleteRole(roleNum);
      logger.debug("Delete Sueccess! roleNum" + roleNum);
      
      BoardDAO boardDAO = new BoardDAO();
      int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
      User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
      BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
      boardDAO.addBoardActivityLog(factory.Activity(oldRole, "삭제"), boardNum);
      
      Gson gson = new Gson();
      String gsonData = gson.toJson(roleNum);
      logger.debug("DeleteRoleServlet gsonData : {}", gsonData);
      PrintWriter out = response.getWriter();
      out.print(gsonData);
    } catch (Exception e) {
      logger.debug("DeleteRoleServlet Error : " + e.getMessage());
    }
  }
}
