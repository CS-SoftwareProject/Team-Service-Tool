package TT.domain.timeline;

public class TimelineComments {
	
	private int TimeLineContent_Num;
	private int Timeline_Num;
	private int comment_Num;
	private String userId;
	private String comment_Content;
	private String comment_date;
	
	public TimelineComments(){
		
	}
	
	public TimelineComments(int TimeLine_Num, int TimeLineContent_Num, String userId, String comment_Content) {
		super();
		this.Timeline_Num = TimeLine_Num;
		this.TimeLineContent_Num = TimeLineContent_Num;
		this.userId = userId;
		this.comment_Content = comment_Content;
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
	public int getComment_Num() {
		return comment_Num;
	}
	public void setComment_Num(int comment_Num) {
		this.comment_Num = comment_Num;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComment_Content() {
		return comment_Content;
	}
	public void setComment_Content(String comment_Content) {
		this.comment_Content = comment_Content;
	}
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}

}
