package TT.domain.timeline;

public class TimelineContents {

  private int Timeline_Num;
  private int TimeLineContent_Num;
  private String userId;
  private String TimeLineContent;
  private String TimeLineContent_date;

  public TimelineContents() {}

  public TimelineContents(int Timeline_Num, String userId, String TimeLineContent) {
    super();
    this.Timeline_Num = Timeline_Num;
    this.userId = userId;
    this.TimeLineContent = TimeLineContent;
  }

  public int getTimeline_Num() {
    return Timeline_Num;
  }

  public void setTimeline_Num(int timeline_Num) {
    Timeline_Num = timeline_Num;
  }

  public int getTimeLineContent_Num() {
    return TimeLineContent_Num;
  }

  public void setTimeLineContent_Num(int timeLineContent_Num) {
    TimeLineContent_Num = timeLineContent_Num;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTimeLineContent() {
    return TimeLineContent;
  }

  public void setTimeLineContent(String timeLineContent) {
    TimeLineContent = timeLineContent;
  }

  public String getTimeLineContent_date() {
    return TimeLineContent_date;
  }

  public void setTimeLineContent_date(String timeLineContent_date) {
    TimeLineContent_date = timeLineContent_date;
  }

  @Override
  public String toString() {
    return "TimelineContents [Timeline_Num=" + Timeline_Num + ", TimeLineContent_Num=" + TimeLineContent_Num + ", userId=" + userId + ", TimeLineContent=" + TimeLineContent + ", TimeLineContent_date=" + TimeLineContent_date + "]";
  }
}
