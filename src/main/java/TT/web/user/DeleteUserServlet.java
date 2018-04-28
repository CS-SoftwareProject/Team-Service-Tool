package TT.web.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.user.UserDAO;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/users/dropuser")
public class DeleteUserServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(UpdateUserServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    HttpSession session = req.getSession();
    User sessionUser = (User) SessionUtils.getObjectValue(session, "user");
    String userId = sessionUser.getUserId();
    logger.debug("" + userId);
    try {
      UserDAO userDAO = new UserDAO();
      session.removeAttribute("user");
      userDAO.removeUser(userId);

    } catch (Exception e) {
      logger.debug("\ndrop fail" + e);
    }

    resp.sendRedirect("/");
  }
}
