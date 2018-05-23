package TT.domain.project;

public class Chat {
  private String writeUser;
  private String userImage; 
  private String chatMessage;
  private String projectName;
  private Long chatTime;
  
  public String getWriteUser() {
    return writeUser;
  }
  public void setWriteUser(String writeUser) {
    this.writeUser = writeUser;
  }
  public String getUserImage() {
    return userImage;
  }
  public void setUserImage(String userImage) {
    this.userImage = userImage;
  }
  public String getChatMessage() {
    return chatMessage;
  }
  public void setChatMessage(String chatMessage) {
    this.chatMessage = chatMessage;
  }
  public String getProjectName() {
    return projectName;
  }
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  public Long getChatTime() {
    return chatTime;
  }
  public void setChatTime(Long chatTime) {
    this.chatTime = chatTime;
  }
  
  
  public Chat() {
  }
  public Chat(String writeUser, String chatMessage, String projectName, Long chatTime) {
    this.writeUser = writeUser;
    this.chatMessage = chatMessage;
    this.projectName = projectName;
    this.chatTime = chatTime;
  }
  public Chat(String writeUser, String userImage, Long chatTime, String chatMessage) {
    this.writeUser = writeUser;
    this.userImage = userImage;
    this.chatMessage = chatMessage;
    this.chatTime = chatTime;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((chatMessage == null) ? 0 : chatMessage.hashCode());
    result = prime * result + ((chatTime == null) ? 0 : chatTime.hashCode());
    result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
    result = prime * result + ((userImage == null) ? 0 : userImage.hashCode());
    result = prime * result + ((writeUser == null) ? 0 : writeUser.hashCode());
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
    Chat other = (Chat) obj;
    if (chatMessage == null) {
      if (other.chatMessage != null)
        return false;
    } else if (!chatMessage.equals(other.chatMessage))
      return false;
    if (chatTime == null) {
      if (other.chatTime != null)
        return false;
    } else if (!chatTime.equals(other.chatTime))
      return false;
    if (projectName == null) {
      if (other.projectName != null)
        return false;
    } else if (!projectName.equals(other.projectName))
      return false;
    if (userImage == null) {
      if (other.userImage != null)
        return false;
    } else if (!userImage.equals(other.userImage))
      return false;
    if (writeUser == null) {
      if (other.writeUser != null)
        return false;
    } else if (!writeUser.equals(other.writeUser))
      return false;
    return true;
  }
  @Override
  public String toString() {
    return "Chat [writeUser=" + writeUser + ", userImage=" + userImage + ", chatMessage=" + chatMessage + ", projectName=" + projectName + ", chatTime=" + chatTime + "]";
  }
  
}
