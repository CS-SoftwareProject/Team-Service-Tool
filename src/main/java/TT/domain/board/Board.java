package TT.domain.board;

public class Board {

  private int boardNum;
  private String projectName;
  private String boardName;
  private int boardProgress;
  private String boardInfo;

  public Board() {

  }
  
  public Board(int boardNum, String projectName, String boardName, int boardProgress, String boardInfo) {
    this.boardNum = boardNum;
    this.projectName = projectName;
    this.boardName = boardName;
    this.boardProgress = boardProgress;
    this.boardInfo = boardInfo;
  }

  public Board(String boardName, String projectName, String boardInfo) {
    this.boardName = boardName;
    this.projectName = projectName;
    this.boardInfo = boardInfo;
  }

  public int getBoardNum() {
    return boardNum;
  }

  public void setBoardNum(int boardNum) {
    this.boardNum = boardNum;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getBoardName() {
    return boardName;
  }

  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }
  
  public int getBoardProgress() {
    return boardProgress;
  }

  public void setBoardProgress(int boardProgress) {
    this.boardProgress = boardProgress;
  }
  
  public String getBoardInfo() {
    return boardInfo;
  }

  public void setBoardInfo(String boardInfo) {
    this.boardInfo = boardInfo;
  }
  
  @Override
  public String toString() {
    return "Board [boardNum=" + boardNum + ", projectName=" + projectName + ", boardName=" + boardName + ", boardProgress=" + boardProgress + ", boardInfo=" + boardInfo + "]";
  }
}
