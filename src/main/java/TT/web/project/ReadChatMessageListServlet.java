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

import com.google.gson.Gson;

import TT.dao.project.ProjectDAO;
import TT.domain.project.Chat;
import TT.service.support.SessionUtils;

@WebServlet("/projects/getChats")
public class ReadChatMessageListServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProjectDAO projectDAO=new ProjectDAO();
    Gson gson=new Gson();
    PrintWriter out=response.getWriter();
    HttpSession session = request.getSession();
    
    List<Chat> list=projectDAO.getChatMessages(SessionUtils.getStringValue(session, "projectName"));
    out.print(gson.toJson(list));
  }
}
