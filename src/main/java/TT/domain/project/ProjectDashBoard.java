package TT.domain.project;

import java.util.List;

import TT.domain.board.Board;
import TT.domain.card.Card;

public class ProjectDashBoard {
  private String projectName;
  private int projectProgress;
  private List<ProjectMember> projectMembers;
  private List<Card> lastCards;
  private List<Board> boards;
  /* private List<Gantt> gantts */


  public ProjectDashBoard() {
    
  }

  public ProjectDashBoard(String projectName, int projectProgress, List<ProjectMember> projectMembers, List<Card> lastCards, List<Board> boardList) {
    this.projectName = projectName;
    this.projectProgress = projectProgress;
    this.projectMembers = projectMembers;
    this.lastCards = lastCards;
    this.boards = boardList;
  }
  
  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public int getProjectProgress() {
    return projectProgress;
  }

  public void setProjectProgress(int projectProgress) {
    this.projectProgress = projectProgress;
  }

  public List<ProjectMember> getProjectMembers() {
    return projectMembers;
  }

  public void setProjectMembers(List<ProjectMember> projectMembers) {
    this.projectMembers = projectMembers;
  }

  public List<Card> getLastCards() {
    return lastCards;
  }

  public void setLastCards(List<Card> lastCards) {
    this.lastCards = lastCards;
  }
  
  public List<Board> getBoards() {
    return boards;
  }

  public void setBoards(List<Board> boards) {
    this.boards = boards;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((boards == null) ? 0 : boards.hashCode());
    result = prime * result + ((lastCards == null) ? 0 : lastCards.hashCode());
    result = prime * result + ((projectMembers == null) ? 0 : projectMembers.hashCode());
    result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
    result = prime * result + projectProgress;
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
    ProjectDashBoard other = (ProjectDashBoard) obj;
    if (boards == null) {
      if (other.boards != null)
        return false;
    } else if (!boards.equals(other.boards))
      return false;
    if (lastCards == null) {
      if (other.lastCards != null)
        return false;
    } else if (!lastCards.equals(other.lastCards))
      return false;
    if (projectMembers == null) {
      if (other.projectMembers != null)
        return false;
    } else if (!projectMembers.equals(other.projectMembers))
      return false;
    if (projectName == null) {
      if (other.projectName != null)
        return false;
    } else if (!projectName.equals(other.projectName))
      return false;
    if (projectProgress != other.projectProgress)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ProjectDashBoard [projectName=" + projectName + ", projectProgress=" + projectProgress + ", projectMembers=" + projectMembers + ", lastCards=" + lastCards + ", boardLists=" + boards + "]";
  }
}
