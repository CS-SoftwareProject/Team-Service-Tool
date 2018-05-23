package TT.domain.user;

public class UserDashBoard {
  private String cardSubject;
  private String roleName;
  private int boardNum;
  private int cardNum;
  private int cardProgress;
  
  public UserDashBoard() {
  }
  
  public UserDashBoard(String cardSubject, String roleName, int cardProgress, int boardNum, int cardNum) {
    this.cardSubject = cardSubject;
    this.roleName = roleName;
    this.cardProgress = cardProgress;
    this.boardNum = boardNum;
    this.cardNum = cardNum;
  }

  public String getCardSubject() {
    return cardSubject;
  }

  public void setCardSubject(String cardSubject) {
    this.cardSubject = cardSubject;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public int getCardProgress() {
    return cardProgress;
  }

  public void setCardProgress(int cardProgress) {
    this.cardProgress = cardProgress;
  }

  public int getBoardNum() {
    return boardNum;
  }

  public void setBoardNum(int boardNum) {
    this.boardNum = boardNum;
  }
  
  public int getCardNum() {
    return cardNum;
  }

  public void setCardNum(int cardNum) {
    this.cardNum = cardNum;
  }
  
  @Override
  public String toString() {
    return "UserDashBoard [cardSubject=" + cardSubject + ", roleName=" + roleName + ", boardNum=" + boardNum + ", cardNum=" + cardNum + ", cardProgress=" + cardProgress + "]";
  }
}
