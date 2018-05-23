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

import TT.dao.timeline.TimelineDAO;
import TT.domain.timeline.Timeline;
import TT.service.support.SessionUtils;

@WebServlet("/timelines/createTimeline")
public class CreateTimelineServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateTimelineServlet.class);
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    SessionUtils sessionUtils = new SessionUtils();
    HttpSession session = request.getSession();
    Timeline timeline = new Timeline();
    TimelineDAO timelineDAO = new TimelineDAO();

    String projectName = sessionUtils.getStringValue(session, "projectName");

    try {
      timeline = new Timeline(projectName);
      logger.debug("Parameters ? {}", timeline);
      response.sendRedirect("/project/timeline");
    } catch (Exception e) {
      logger.debug("error : " + e.getMessage());
    }
  }
}
