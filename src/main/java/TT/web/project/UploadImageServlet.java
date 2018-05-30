package TT.web.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import TT.dao.project.ProjectDAO;
import TT.domain.project.Image;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/imageUpload")
public class UploadImageServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(UploadImageServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    final String path ="/upload_image/";
    MultipartRequest mr = new MultipartRequest(request, request.getRealPath(path), 1024 * 1024 * 5, "utf-8", new DefaultFileRenamePolicy());
    ProjectDAO projectDAO = new ProjectDAO();

    HttpSession session = request.getSession();
    
    User sessionUser=(User)SessionUtils.getObjectValue(session, "user");
    String userId = sessionUser.getUserId();
    
    Image image = new Image(path + mr.getFile("uploadFile").getName(), userId);

    projectDAO.addImage(image, SessionUtils.getStringValue(session, "projectName"));
    response.sendRedirect("lists/cardlist?boardNum=" + SessionUtils.getIntegerValue(session, "boardNum"));
  }
}