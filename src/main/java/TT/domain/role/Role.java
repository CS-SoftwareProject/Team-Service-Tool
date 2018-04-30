package TT.domain.role;

public class Role {

  private int roleNum;
  private String roleName;
  private int bardNum;

  public Role() {

  }

  public Role(int roleNum, String roleName, int bardNum) {
    this.roleNum = roleNum;
    this.roleName = roleName;
    this.bardNum = bardNum;
  }

  public Role(String roleName, int bardNum) {
    this.roleName = roleName;
    this.bardNum = bardNum;
  }

  public int getRoleNum() {
    return roleNum;
  }

  public void setRoleNum(int roleNum) {
    this.roleNum = roleNum;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public int getBoardNum() {
    return bardNum;
  }

  public void setBoardNum(int bardNum) {
    this.bardNum = bardNum;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + bardNum;
    result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
    result = prime * result + roleNum;
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
    Role other = (Role) obj;
    if (bardNum != other.bardNum)
      return false;
    if (roleName == null) {
      if (other.roleName != null)
        return false;
    } else if (!roleName.equals(other.roleName))
      return false;
    if (roleNum != other.roleNum)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Role [roleNum=" + roleNum + ", roleName=" + roleName + ", bardNum=" + bardNum +"]";
  }
}
