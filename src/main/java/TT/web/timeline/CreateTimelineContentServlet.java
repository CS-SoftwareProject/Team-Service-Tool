package TT.web.timeline;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.timeline.TimelineContentsDAO;
import TT.domain.timeline.TimelineContents;

@WebServlet("/timelines/createContent")
public class CreateTimelineContentServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateTimelineServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    TimelineContents timelineContent = new TimelineContents();
    TimelineContentsDAO timelineDAO = new TimelineContentsDAO();

    String userId = request.getParameter("userId");
    int timelineNum = Integer.parseInt(request.getParameter("timelineNum"));
    String timeLineContent = request.getParameter("timeLineContent");
    logger.debug("debug ? userId : " + userId + " timelineNum : " + timelineNum + "timelineContent : " + timelineContent);;

    try {
      timelineContent = new TimelineContents(timelineNum, userId, timeLineContent);
      logger.debug("Parameters ? {}", timelineContent);
      timelineDAO.addTimelineContent(timelineContent);
      response.sendRedirect("/project/timeline");
    } catch (Exception e) {
      logger.debug("error : " + e.getMessage());
    }
  }
}
