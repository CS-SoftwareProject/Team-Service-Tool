package TT.domain.card;

import java.sql.Timestamp;
import java.util.List;

import TT.domain.assignee.Assignee;

public class Card {
  private int cardNum;
  private String userId;
  private String subject;
  private String content;
  private Timestamp modifyTime;
  private int listNum;
  private int cardOrder;
  private int progress;
  private int level;
  private String status;
  private boolean canWrite;
  private long start;
  private int duration;
  private boolean assigUnchanged;
  private boolean unchanged;
  private List<Assignee> assigs;
  private boolean hasChild;
  private int taskOrder;
  private String boardName;
  private int boardNum;

  public Card() {}

  public Card(int cardNum, String subject, String userId, int listiNum) {
    this.cardNum = cardNum;
    this.subject = subject;
    this.userId = userId;
    this.listNum = listiNum;
  }

  public Card(int cardNum, String userId, String subject, String content, Timestamp modifyTime, int listNum, int cardOrder, long start ,int progress) {
    this.cardNum = cardNum;
    this.userId = userId;
    this.subject = subject;
    this.content = content;
    this.cardOrder = cardOrder;
    this.modifyTime = modifyTime;
    this.listNum = listNum;
    this.start = start;
    this.progress = progress;
  }

  public Card(int cardNum, String userId, String subject, String content, int progress, int level, String status, long start, int duration, boolean hasChild, boolean canWrite) {
    this.cardNum = cardNum;
    this.userId = userId;
    this.subject = subject;
    this.content = content;
    this.progress = progress;
    this.level = level;
    this.status = status;
    this.start = start;
    this.duration = duration;
    this.hasChild = hasChild;
    this.canWrite = canWrite;
  }

  public Card(String userId, String subject, String content, int listNum, int progress, int level, String status, boolean canWrite, long start, int duration, boolean hasChild) {
    this.userId = userId;
    this.subject = subject;
    this.content = content;
    this.listNum = listNum;
    this.progress = progress;
    this.level = level;
    this.status = status;
    this.canWrite = canWrite;
    this.start = start;
    this.duration = duration;
    this.hasChild = hasChild;
  }

  public Card(String userId, String subject, String content, int listNum, int cardOrder, int progress) {
    this.userId = userId;
    this.subject = subject;
    this.content = content;
    this.listNum = listNum;
    this.cardOrder = cardOrder;
    this.progress = progress;
  }

  public Card(int cardNum, String userId, String subject, String content, Timestamp modifyTime, int listNum, int cardOrder) {
    this.cardNum = cardNum;
    this.userId = userId;
    this.subject = subject;
    this.content = content;
    this.modifyTime = modifyTime;
    this.listNum = listNum;
    this.cardOrder = cardOrder;
  }

  public Card(int listNum, int cardOrder) {
    this.listNum = listNum;
    this.cardOrder = cardOrder;
  }
  
  public Card(int cardNum, long start) {
    this.cardNum = cardNum;
    this.start = start;
  }

  public int getCardNum() {
    return cardNum;
  }

  public void setCardNum(int cardNum) {
    this.cardNum = cardNum;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Timestamp modifyTime) {
    this.modifyTime = modifyTime;
  }

  public int getListNum() {
    return listNum;
  }

  public void setListNum(int listNum) {
    this.listNum = listNum;
  }

  public int getCardOrder() {
    return cardOrder;
  }

  public void setCardOrder(int cardOrder) {
    this.cardOrder = cardOrder;
  }

  public List<Assignee> getAssignees() {
    return assigs;
  }

  public void setAssignees(List<Assignee> assignees) {
    this.assigs = assignees;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public String getBoardName() {
    return boardName;
  }

  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public boolean isCanWrite() {
    return canWrite;
  }

  public void setCanWrite(boolean canWrite) {
    this.canWrite = canWrite;
  }

  public long getStart() {
    return start;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public boolean isAssigUnchanged() {
    return assigUnchanged;
  }

  public void setAssigUnchanged(boolean assigUnchanged) {
    this.assigUnchanged = assigUnchanged;
  }

  public boolean isUnchanged() {
    return unchanged;
  }

  public void setUnchanged(boolean unchanged) {
    this.unchanged = unchanged;
  }

  public boolean hasChild() {
    return hasChild;
  }

  public void setChild(boolean hasChild) {
    this.hasChild = hasChild;
  }

  public boolean isHasChild() {
    return hasChild;
  }

  public void setHasChild(boolean hasChild) {
    this.hasChild = hasChild;
  }

  public int getTaskOrder() {
    return taskOrder;
  }

  public void setTaskOrder(int taskOrder) {
    this.taskOrder = taskOrder;
  }

  public int getBoardNum() {
    return boardNum;
  }

  public void setBoardNum(int boardNum) {
    this.boardNum = boardNum;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (assigUnchanged ? 1231 : 1237);
    result = prime * result + ((assigs == null) ? 0 : assigs.hashCode());
    result = prime * result + (canWrite ? 1231 : 1237);
    result = prime * result + cardNum;
    result = prime * result + cardOrder;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    result = prime * result + duration;
    result = prime * result + (hasChild ? 1231 : 1237);
    result = prime * result + level;
    result = prime * result + listNum;
    result = prime * result + ((modifyTime == null) ? 0 : modifyTime.hashCode());
    result = prime * result + progress;
    result = prime * result + (int) (start ^ (start >>> 32));
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((subject == null) ? 0 : subject.hashCode());
    result = prime * result + (unchanged ? 1231 : 1237);
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Card other = (Card) obj;
    if (assigUnchanged != other.assigUnchanged)
      return false;
    if (assigs == null) {
      if (other.assigs != null)
        return false;
    } else if (!assigs.equals(other.assigs))
      return false;
    if (canWrite != other.canWrite)
      return false;
    if (cardNum != other.cardNum)
      return false;
    if (cardOrder != other.cardOrder)
      return false;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
    if (duration != other.duration)
      return false;
    if (hasChild != other.hasChild)
      return false;
    if (level != other.level)
      return false;
    if (listNum != other.listNum)
      return false;
    if (modifyTime == null) {
      if (other.modifyTime != null)
        return false;
    } else if (!modifyTime.equals(other.modifyTime))
      return false;
    if (progress != other.progress)
      return false;
    if (start != other.start)
      return false;
    if (status == null) {
      if (other.status != null)
        return false;
    } else if (!status.equals(other.status))
      return false;
    if (subject == null) {
      if (other.subject != null)
        return false;
    } else if (!subject.equals(other.subject))
      return false;
    if (unchanged != other.unchanged)
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "Card [cardNum=" + cardNum + ", userId=" + userId + ", subject=" + subject + ", content=" + content + ", modifyTime=" + modifyTime + ", listNum=" + listNum + ", cardOrder=" + cardOrder + ", progress=" + progress + ", level=" + level + ", status=" + status + ", canWrite=" + canWrite + ", start=" + start + ", duration=" + duration + ", assigUnchanged=" + assigUnchanged + ", unchanged=" + unchanged + ", assigs=" + assigs + ", hasChild=" + hasChild + ", taskOrder=" + taskOrder + ", boardName=" + boardName + "]";
  }
}