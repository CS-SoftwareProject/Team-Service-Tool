package TT.domain.timeline;

public class Timeline {
	
	private int timelineNum;
	private String projectName;

	public Timeline() {
	}
	
	public Timeline(String projectName) {
		super();
		this.projectName = projectName;
	}

	public int getTimelineNum() {
		return timelineNum;
	}

	public void setTimelineNum(int timelineNum) {
		this.timelineNum = timelineNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
