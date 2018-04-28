package TT.web.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/users/updateForm")
public class UpdateFormUserServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(UpdateFormUserServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    User sessionUser=(User)SessionUtils.getObjectValue(session, "user");
    String userId = sessionUser.getUserId();

    if (userId == null) {
      response.sendRedirect("/");
      return;
    }
    logger.debug("User Id : " + userId);

    UserDAO userDAO = new UserDAO();

    User user = userDAO.getByUserId(userId);
    request.setAttribute("user", user);
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
    rd.forward(request, response);
  }
}
