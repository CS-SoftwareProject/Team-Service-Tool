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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import TT.dao.project.ProjectDAO;
import TT.domain.user.User;

@WebServlet("/projects/searchUser")
public class ReadUserListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadUserListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<User> list = new ArrayList<User>();
    ProjectDAO projectDAO = new ProjectDAO();
    HttpSession session = request.getSession();

    session.removeAttribute("keyword");
    
    String projectName = (String)session.getAttribute("projectName");
    String keyword = request.getParameter("keyword");
   
    session.setAttribute("keyword", keyword);

    logger.debug("검색 키 : " + keyword);
    logger.debug("Session_projectName = " + projectName);
    try {
      list = projectDAO.getUserList(keyword, projectName);
      
      Gson gson = new Gson();
      JsonElement el = gson.toJsonTree(1);
      JsonArray arr = new JsonArray();
      String invitedUserId;
      
      for(int i = 0; i < list.size(); i++) {
        invitedUserId = projectDAO.getInvitedUserId(list.get(i).getUserId());
        
        el = gson.toJsonTree(list.get(i));
        JsonObject obj = new JsonObject();
        obj.add("user", el);
        
        if (invitedUserId == null)
          obj.add("invited", gson.toJsonTree(false));
        else
          obj.add("invited", gson.toJsonTree(true));
        
        arr.add(obj);
      }
      
      String jsonData = gson.toJson(arr);

      PrintWriter out = response.getWriter();
      out.print(jsonData);

    } catch (Exception e) {
      logger.debug("Search fail : " + e);
    }
  }
}
