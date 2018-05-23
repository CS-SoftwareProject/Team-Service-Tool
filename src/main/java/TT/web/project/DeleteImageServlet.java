package TT.web.project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import TT.dao.project.ProjectDAO;

@WebServlet("/project/imagedelete")
public class DeleteImageServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(DeleteImageServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProjectDAO projectDAO = new ProjectDAO();
    PrintWriter out=response.getWriter();
    String jsonData = request.getParameter("Image_List");
    JsonParser jsonParser=new JsonParser();
    JsonArray jsonArray=jsonParser.parse(jsonData).getAsJsonArray();
    logger.debug("jsonArray: {}",jsonArray);
    for(JsonElement index:jsonArray){
      String imagePath=index.getAsString();
      logger.debug("imagePath: {}",imagePath);
      File f = new File("C:/web-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/viser" + imagePath);
      try {
        if (f.delete()) {
          logger.debug("파일 삭제 성공");
        }
        projectDAO.removeImage(imagePath);
      } catch (Exception e) {
        logger.debug("파일 삭제 실패");
      }
    }
    out.print(true);
  }
}
