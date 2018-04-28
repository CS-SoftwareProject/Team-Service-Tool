package TT.domain.project;

import java.sql.Date;

public class Project {
  private String projectName;
  private Date projectDate;

  public Project() {

  }

  public Project(String projectName) {
    this.projectName = projectName;
  }

  public Project(String projectName, Date projectDate) {
    this.projectName = projectName;
    this.projectDate = projectDate;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public Date getProjectDate() {
    return projectDate;
  }

  public void setProjectDate(Date projectDate) {
    this.projectDate = projectDate;
  }

  @Override
  public String toString() {
    return "Project [projectName=" + projectName + ", projectDate=" + projectDate + "]";
  }

}
