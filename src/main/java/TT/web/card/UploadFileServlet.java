package TT.web.card;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/fileUpload")
public class UploadFileServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    final String path = request.getRealPath("/upload_file");
    MultipartRequest mr = new MultipartRequest(request, path, 1024 * 1024 * 5, "utf-8", new DefaultFileRenamePolicy());

    response.sendRedirect("/WEB-INF/jsp/index.jsp");
  }
}
