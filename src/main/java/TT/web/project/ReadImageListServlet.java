package TT.web.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import TT.dao.project.ProjectDAO;
import TT.service.support.SessionUtils;

@WebServlet("/project/imagelist")
public class ReadImageListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadImageListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<String> imagelist = new ArrayList<String>();
    ProjectDAO projectDAO = new ProjectDAO();
    HttpSession session = request.getSession();
    Gson gson=new Gson();
    PrintWriter out= response.getWriter();
    
    imagelist = projectDAO.getImageList(SessionUtils.getStringValue(session, "projectName"));
    String jsonData=gson.toJson(imagelist);
    logger.debug("response 보냄");
    out.print(jsonData);
    
  }
}
