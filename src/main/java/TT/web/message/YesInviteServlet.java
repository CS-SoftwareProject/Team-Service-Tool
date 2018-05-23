package TT.web.message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import TT.dao.message.MessageDAO;
import TT.dao.project.ProjectDAO;
import TT.service.support.InviteMessageFactory;

@WebServlet("/message/yesjoin")
public class YesInviteServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(YesInviteServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProjectDAO projectDAO = new ProjectDAO();
    MessageDAO msgDAO = new MessageDAO();
    
    String projectName = request.getParameter("projectName");
    String userId = request.getParameter("receiver");
    int msgNum = Integer.parseInt(request.getParameter("msgNum"));
    
    InviteMessageFactory factory = new InviteMessageFactory(projectName, userId);

    logger.debug("[para] projectName : " + projectName + " joiner : " + userId + " msgNum : " + msgNum);
    projectDAO.updatePrjMemberIsJoin(userId, projectName);
    msgDAO.updateMessageContent(factory.whenYesInviteContent(projectName), msgNum);
    
    Gson gson = new Gson();
    String gsonData = gson.toJson(projectName);
    PrintWriter out = response.getWriter();
    out.print(gsonData);
  }
}
