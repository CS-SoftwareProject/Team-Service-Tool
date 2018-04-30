package TT.web.role;

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

import TT.dao.role.RoleDAO;

@WebServlet("/roles/deleteRole")
public class DeleteRoleServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(DeleteRoleServlet.class);
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RoleDAO roleDAO = new RoleDAO();
    int roleNum = Integer.parseInt(request.getParameter("roleNum"));
    logger.debug("Get Para = {}", request.getParameter("roleNum"));
    
    try {
      roleDAO.deleteRole(roleNum);
      logger.debug("Delete Sueccess! roleNum" + roleNum);
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
