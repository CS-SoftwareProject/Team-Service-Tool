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

@WebServlet("/roles/createRole")
public class CreateRoleServlet extends HttpServlet{
  private static final Logger logger = LoggerFactory.getLogger(CreateRoleServlet.class);
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    RoleDAO roleDAO = new RoleDAO();

    String roleName = request.getParameter("roleName");
    int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
    
    try {
      Role role = roleDAO.addRole(roleName, boardNum);
      
      BoardDAO boardDAO = new BoardDAO(); 
      User sessionUser = (User)SessionUtils.getObjectValue(session, "user");
      BoardActivitivationFactory factory = new BoardActivitivationFactory(sessionUser.getUserId());
      boardDAO.addBoardActivityLog(factory.Activity(role, "추가"), boardNum);
      
      logger.debug("Role : {}", role);
      Gson gson = new Gson();
      String gsonData = gson.toJson(role);
      logger.debug("Role GsonData : {}", gsonData);
      PrintWriter out = response.getWriter();
      out.print(gsonData);
    } catch (Exception e) {
      logger.debug("CreateRoleServlet Error : " + e.getMessage());
    }
  }
}
