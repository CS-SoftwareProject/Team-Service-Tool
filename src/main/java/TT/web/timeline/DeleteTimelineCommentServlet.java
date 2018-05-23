package TT.web.timeline;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.timeline.TimelineCommentsDAO;
import TT.service.support.SessionUtils;

@WebServlet("/timelines/deleteComment")
public class DeleteTimelineCommentServlet extends HttpServlet {
  private static Logger logger = LoggerFactory.getLogger(ReadTimelineServlet.class);
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    SessionUtils sessionUtils = new SessionUtils();
    HttpSession session = request.getSession();
    TimelineCommentsDAO timelinecontentDAO = new TimelineCommentsDAO();

    int commentNum = Integer.parseInt(request.getParameter("commentNum"));

    try {
      logger.debug("Parameters ? {}", commentNum);
      timelinecontentDAO.removeTimelineComment(commentNum);
      response.sendRedirect("/project/timeline");
    } catch (Exception e) {
      logger.debug("error : " + e.getMessage());
    }
  }
}
