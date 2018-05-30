package TT.web.timeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TT.dao.timeline.TimelineCommentsDAO;
import TT.dao.timeline.TimelineContentsDAO;
import TT.dao.timeline.TimelineDAO;
import TT.domain.timeline.Timeline;
import TT.domain.timeline.TimelineComments;
import TT.domain.timeline.TimelineContents;
import TT.service.support.SessionUtils;

@WebServlet("/project/timeline")
public class ReadTimelineServlet extends HttpServlet {
  private static Logger logger = LoggerFactory.getLogger(ReadTimelineServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    SessionUtils sessionUtils = new SessionUtils();
    HttpSession session = request.getSession();
    List<TimelineContents> timelinecontentlist = new ArrayList<TimelineContents>();
    List<TimelineComments> timelinecommentlist = new ArrayList<TimelineComments>();

    Timeline timeline = new Timeline();

    TimelineDAO timelineDAO = new TimelineDAO();
    TimelineContentsDAO timelinecontentDAO = new TimelineContentsDAO();
    TimelineCommentsDAO timelinecommentDAO = new TimelineCommentsDAO();
    
    String projectName = sessionUtils.getStringValue(session, "projectName");
    timeline = timelineDAO.getTimeline(projectName);
    int timelineNum = timeline.getTimelineNum();

    timelinecontentlist = timelinecontentDAO.getContent(timelineNum);
    timelinecommentlist = timelinecommentDAO.getContent(timelineNum);

    request.setAttribute("isReadBoard", true);
    request.setAttribute("isTimeLine", true);
    request.setAttribute("TLlist", timelinecontentlist);
    request.setAttribute("CLlist", timelinecommentlist);
    request.setAttribute("projectName", projectName);
    request.setAttribute("timelineNum", timelineNum);
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/timeline.jsp");
    rd.forward(request, response);
  }
}

