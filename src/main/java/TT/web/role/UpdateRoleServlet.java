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

import com.google.gson.JsonArray;

import TT.dao.board.BoardDAO;
import TT.dao.role.RoleDAO;
import TT.domain.role.Role;
import TT.domain.user.User;
import TT.service.support.SessionUtils;
import TT.service.support.Activitiy.BoardActivitivationFactory;

@WebServlet("/roles/updateRole")
public class UpdateRoleServlet extends HttpServlet{
  private static final Logger logger = LoggerFactory.getLogger(UpdateRoleServlet.class);
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RoleDAO roleDAO = new RoleDAO();
    HttpSession session = request.getSession();
    
    int roleNum = Integer.parseInt(request.getParameter("roleNum"));
    String roleName = request.getParameter("roleName");
    logger.debug("UpdateRoleServlet Para : {}", roleNum);
    logger.debug("UpdateRoleServlet Para : {}", roleName);
    try {
      Role oldRole = roleDAO.findByRoleNum(roleNum);
      roleDAO.updateRole(roleNum, roleName);
      
      BoardDAO boardDAO = new BoardDAO();
      int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
      User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
      BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
      boardDAO.addBoardActivityLog(factory.updateActivity(oldRole, oldRole.getRoleName(), roleName), boardNum);
      
      JsonArray arr = new JsonArray();
      arr.add(roleName);
      arr.add(roleNum);
      logger.debug("UpdateRoleServlet gsonData : {}", arr);
      PrintWriter out = response.getWriter();
      out.print(arr);
    } catch (Exception e) {
      logger.debug("UpdateRoleServlet error : " + e.getMessage());
    }
  }
}
