package TT.domain.board;

import java.sql.Date;

public class BoardActivityLog {
  private int logNum;
  private int activityNum;
  private String activity;
  private Date activityDate;

  public BoardActivityLog() {

  }

  public BoardActivityLog(int logNum, int activityNum, String activity, Date activityDate) {
    this.logNum = logNum;
    this.activityNum = activityNum;
    this.activity = activity;
    this.activityDate = activityDate;
  }

  public int getLogNum() {
    return logNum;
  }

  public void setLogNum(int logNum) {
    this.logNum = logNum;
  }

  public int getActivityNum() {
    return activityNum;
  }

  public void setActivityNum(int activityNum) {
    this.activityNum = activityNum;
  }

  public String getActivity() {
    return activity;
  }

  public void setActivity(String activity) {
    this.activity = activity;
  }

  public Date getActivityDate() {
    return activityDate;
  }

  public void setActivityDate(Date activityDate) {
    this.activityDate = activityDate;
  }

  @Override
  public String toString() {
    return "BoardActivityLog [logNum=" + logNum + ", activityNum=" + activityNum + ", activity=" + activity + ", activityDate=" + activityDate + "]";
  }
}
