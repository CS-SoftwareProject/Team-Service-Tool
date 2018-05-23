package TT.web.project;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.google.gson.JsonObject;

import TT.dao.project.ProjectDAO;
import TT.domain.project.ProjectMember;
import TT.domain.user.User;
import TT.service.support.SessionUtils;

@WebServlet("/project/memberlist")
public class ReadProjectMemberListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadProjectMemberListServlet.class);

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    List<ProjectMember> memberlist;
    ProjectDAO projectDAO = new ProjectDAO();
    Gson gson=new Gson();
    String list;
    PrintWriter out=response.getWriter();
    JsonObject jsonObject=new JsonObject();
    
    memberlist = projectDAO.getProjectMemberList((String) session.getAttribute("projectName"));
    list=gson.toJson(memberlist);
    jsonObject.addProperty("list",list);
    
    User sessionUser=(User)SessionUtils.getObjectValue(session, "user");
    for(ProjectMember pm:memberlist){
      if(pm.getPower()==1&&pm.getUserId().equals(sessionUser.getUserId())){
        jsonObject.addProperty("isMaster", new Boolean(true));
      }
    }
    if(!jsonObject.has("isMaster")) jsonObject.addProperty("isMaster", new Boolean(false));
    out.println(jsonObject);
  }
}
