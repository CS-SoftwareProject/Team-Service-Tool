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
import TT.domain.timeline.TimelineComments;
import TT.service.support.SessionUtils;

@WebServlet("/timelines/createComment")
public class CreateTimelineCommentServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateTimelineServlet.class);
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    SessionUtils sessionUtils = new SessionUtils();
    HttpSession session = request.getSession();
    TimelineComments timelinecomment;
    TimelineCommentsDAO timelinecommentDAO = new TimelineCommentsDAO();


    String userId = request.getParameter("userId");
    int timeline_num = Integer.parseInt(request.getParameter("timelineNum"));
    int timelinecontent_num = Integer.parseInt(request.getParameter("timelineContent_num"));
    String comment = request.getParameter("comment");

    try {
      timelinecomment = new TimelineComments(timeline_num, timelinecontent_num, userId, comment);
      logger.debug("Parameters ? {}", timelinecomment);
      timelinecommentDAO.addTimelineComment(timelinecomment);
      response.sendRedirect("/project/timeline");
    } catch (Exception e) {
      logger.debug("error : " + e.getMessage());
    }
  }
}
