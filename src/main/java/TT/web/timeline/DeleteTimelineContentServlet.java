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

import TT.dao.timeline.TimelineContentsDAO;
import TT.service.support.SessionUtils;

@WebServlet("/timelines/deleteContent")
public class DeleteTimelineContentServlet extends HttpServlet {
  private static Logger logger = LoggerFactory.getLogger(ReadTimelineServlet.class);
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    SessionUtils sessionUtils = new SessionUtils();
    HttpSession session = request.getSession();
    TimelineContentsDAO timelinecontentDAO = new TimelineContentsDAO();

    int timelineNum = Integer.parseInt(request.getParameter("timelineNum"));

    try {
      logger.debug("Parameters ? {}", timelineNum);
      timelinecontentDAO.removeTimelineContent(timelineNum);
      response.sendRedirect("/project/timeline");
    } catch (Exception e) {
      logger.debug("error : " + e.getMessage());
    }
  }
}
