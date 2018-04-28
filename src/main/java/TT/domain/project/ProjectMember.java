package TT.domain.project;

public class ProjectMember {
  private int num;
  private String userId;
  private String userName;
  private String userImage;
  private String projectName;
  private int power;
  private String imagePath;

  public ProjectMember() {
    
  }
  
  public ProjectMember(String userId, String imagePath) {
    this.userId = userId;
    this.imagePath = imagePath;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }
  
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserImage() {
    return userImage;
  }

  public void setUserImage(String userImage) {
    this.userImage = userImage;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public int getPower() {
    return power;
  }

  public void setPower(int power) {
    this.power = power;
  }

  @Override
  public String toString() {
    return "ProjectMember [num=" + num + ", userId=" + userId + ", projectName=" + projectName + ", power=" + power + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + num;
    result = prime * result + power;
    result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
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
    ProjectMember other = (ProjectMember) obj;
    if (num != other.num)
      return false;
    if (power != other.power)
      return false;
    if (projectName == null) {
      if (other.projectName != null)
        return false;
    } else if (!projectName.equals(other.projectName))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }
}
