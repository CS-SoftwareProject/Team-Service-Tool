package TT.web.user;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import TT.dao.user.UserDAO;
import TT.domain.user.User;
import TT.service.support.MyvalidatorFactory;
import TT.service.support.SessionUtils;

@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(UpdateUserServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    User sessionUser = (User) SessionUtils.getObjectValue(session, "user");
    if (sessionUser == null) {
      response.sendRedirect("/");
      logger.debug("UpdateUserServlet error");
      return;
    }

    String path = "/upload_image/";
    MultipartRequest mr = new MultipartRequest(request, request.getRealPath(path), 1024 * 1024 * 5, "utf-8", new DefaultFileRenamePolicy());

    String userId = mr.getParameter("userId");
    String password = mr.getParameter("password");
    String name = mr.getParameter("name");
    String birth = mr.getParameter("birth");
    String email = mr.getParameter("email");

    User user = new User(userId, password, name, birth, email);

    Validator validator = MyvalidatorFactory.createValidator();
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    if (constraintViolations.size() > 0) {
      request.setAttribute("user", user);
      String errorMessage = constraintViolations.iterator().next().getMessage();
      errorForward(request, response, errorMessage);
      return;
    }

    UserDAO userDAO = new UserDAO();

    try {
      File uploadImage = mr.getFile("image");
      logger.debug("imageFile: {}", uploadImage);

      String filePath;

      if (uploadImage != null) {
        filePath = path + uploadImage.getName();
        user.setImage(filePath);
        userDAO.updateUserWithFile(user);
      } else {
        filePath = userDAO.getByUserId(userId).getImage();
        user.setImage(filePath);
        userDAO.updateUser(user);
      }
      session.setAttribute("user", user);
    } catch (SQLException e) {
      logger.debug("SQL Exception error" + e);
    }

    response.sendRedirect("/users/userDashBoard");
  }

  private void errorForward(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
    request.setAttribute("formErrorMessage", errorMessage);
    request.setAttribute("isUpdate", true);
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
    rd.forward(request, response);
  }
}
