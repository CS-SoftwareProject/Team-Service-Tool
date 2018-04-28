package TT.domain.assignee;

public class Assignee {

    private int assigneeNum;
    private int projectMemberNum;
    private int roleNum;
    private int cardNum;
    private String roleName;
    private String userId;
    
    public Assignee() {
      
    }
    
    public Assignee(int assigneeNum, String userId, String roleName) {
      this.assigneeNum = assigneeNum;
      this.userId = userId;
      this.roleName = roleName;
    }
    
    public Assignee(int assigneeNum, int projectMemberNum, int roleNum, int cardNum) {
      this.assigneeNum = assigneeNum;
      this.projectMemberNum = projectMemberNum;
      this.roleNum = roleNum;
      this.cardNum = cardNum;
    }
   
    public Assignee(int assigneeNum, int cardNum, String roleName, int roleNum, String userId) {
      this.assigneeNum = assigneeNum;
      this.cardNum = cardNum;
      this.roleName = roleName;
      this.roleNum = roleNum;
      this.userId = userId;
    }
    
    public int getAssigneeNum() {
      return assigneeNum;
    }

    public void setAssigneeNum(int assigneeNum) {
      this.assigneeNum = assigneeNum;
    }

    public int getProjectMemberNum() {
      return projectMemberNum;
    }

    public void setProjectMemberNum(int projectMemberNum) {
      this.projectMemberNum = projectMemberNum;
    }

    public int getRoleNum() {
      return roleNum;
    }

    public void setRoleNum(int roleNum) {
      this.roleNum = roleNum;
    }

    public int getCardNum() {
      return cardNum;
    }

    public void setCardNum(int cardNum) {
      this.cardNum = cardNum;
    }
    
    public String getRoleName() {
      return roleName;
    }

    public void setRoleName(String roleName) {
      this.roleName = roleName;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    @Override
    public String toString() {
      return "Assignee [assigneeNum=" + assigneeNum + ", projectMemberNum=" + projectMemberNum + ", roleNum=" + roleNum + ", cardNum=" + cardNum + ", roleName=" + roleName + ", userId=" + userId + "]";
    }
    
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + assigneeNum;
      result = prime * result + cardNum;
      result = prime * result + projectMemberNum;
      result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
      result = prime * result + roleNum;
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
      Assignee other = (Assignee) obj;
      if (assigneeNum != other.assigneeNum)
        return false;
      if (cardNum != other.cardNum)
        return false;
      if (projectMemberNum != other.projectMemberNum)
        return false;
      if (roleName == null) {
        if (other.roleName != null)
          return false;
      } else if (!roleName.equals(other.roleName))
        return false;
      if (roleNum != other.roleNum)
        return false;
      if (userId == null) {
        if (other.userId != null)
          return false;
      } else if (!userId.equals(other.userId))
        return false;
      return true;
    }
}
