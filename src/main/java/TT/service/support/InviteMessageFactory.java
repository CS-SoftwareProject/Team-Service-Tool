package TT.service.support;

public class InviteMessageFactory {
  private String subject;
  private String content;
  
  public InviteMessageFactory(String projectName, String receiver) {
    this.content = "";
    this.content += "<input type='hidden' name='projectName' id='projectName' value=" + projectName + ">";
    this.content += "<input type='hidden' name='receiver' id='receiver' value=" + receiver + ">";
    this.content += "<h3 class='timeline-header'><a href='#'>" + "'" +projectName + "'</a> 로 당신을 초대합니다 !!</h3>";
    this.content += "<div class='timeline-body'>";
    this.content += projectName + " 프로젝트에서 초대메시지가 도착하였습니다. YES 버튼을 누르시면, 해당 프로젝트에 구성원이 됩니다.";
    this.content += "</div>";
    this.content += "<div class='timeline-footer'>";
    this.subject = "";
    this.subject += projectName + " 프로젝트에 초대합니다 !!";
  }
  
  public String getSubject() {
    return subject;
  }

  public String getContent() {
    this.content += "<a href='javascript:YesInviteMsg()' class='btn btn-primary btn-xs'>YES</a>";
    this.content += "<a href='javascript:noInviteMsg();' class='btn btn-danger btn-xs'>NO</a>";
    this.content += "</div>";
    this.content += "</div>";
    return content;
  }
  
  public String whenNoInviteContent() {
    this.content += "<a class='btn btn-danger btn-xs'>초대를 거절한 상태입니다.</a>";
    this.content += "</div>";
    this.content += "</div>";
    return content;
  }
  
  public String whenYesInviteContent(String projectName) {
    this.content +=  "<a href='/board/boardlist?projectName=" + projectName + "'" + "class='btn btn-primary btn-xs'>클릭하셔서 프로젝트를 살펴보세요!</a>";
    this.content += "</div>";
    this.content += "</div>";
    return content;
  }
}
