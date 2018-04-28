package TT.web.user;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import TT.dao.user.UserDAO;
import TT.domain.user.User;
import TT.service.support.MyvalidatorFactory;

@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    MultipartRequest mr = new MultipartRequest(request, request.getRealPath(""), 1024 * 1024 * 5, "utf-8", new DefaultFileRenamePolicy());
    
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
    user.setImage("/upload_image/default.png");
    userDAO.addUser(user);

    response.sendRedirect("/login.jsp");
  }

  private void errorForward(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
    request.setAttribute("formErrorMessage", errorMessage);
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
    rd.forward(request, response);
  }
}