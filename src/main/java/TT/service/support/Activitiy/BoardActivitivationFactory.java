package TT.service.support.Activitiy;

import TT.domain.assignee.Assignee;
import TT.domain.card.Card;
import TT.domain.cardlist.CardList;
import TT.domain.role.Role;

public class BoardActivitivationFactory {
  private String startText;
  private String textOfDomain;

  public BoardActivitivationFactory() {

  }

  public BoardActivitivationFactory(String actorUserId) {
    this.startText = "<a href='#'>'" + actorUserId + "'</a>님이 ";
  }

  public <C extends Card, R extends Role, L extends CardList> String Activity(Object domain, String cdAction) {
    if (domain instanceof Card) {
      C card = (C) domain;
      this.textOfDomain = "카드 <a href='#'>'" + card.getSubject() + "'</a>를  <a href='#'>" + cdAction + "</a>하였습니다.";
      return startText.concat(textOfDomain);
    }

    if (domain instanceof Role) {
      R role = (R) domain;
      this.textOfDomain = "역할  <a href='#'>'" + role.getRoleName() + "'</a>를 <a href='#'>" + cdAction + "</a>하였습니다.";
      return startText.concat(textOfDomain);
    }

    if (domain instanceof CardList) {
      L cardList = (L) domain;
      this.textOfDomain = "리스트  <a href='#'>'" + cardList.getListName() + "'</a>를 <a href='#'>" + cdAction + "</a>하였습니다.";
      return startText.concat(textOfDomain);
    }
    return null;
  }

  public String activityInCard(String cardName, Assignee assignee, String cdAction) {
    return startText.concat(this.textOfDomain = "<a href='#'>'" + cardName + "'</a>카드에 담당자 <a href='#'>'" + assignee.getUserId() + ": " + assignee.getRoleName() + "'</a>를   <a href='#'>" + cdAction + "</a>하였습니다.");
  }

  public String updateActivityInCard(String cardName, Assignee preAssignee, Assignee newAssignee) {
    return startText.concat(this.textOfDomain = "<a href='#'>'" + cardName + "'</a>카드에 담당자 <a href='#'>'" + preAssignee.getUserId() + ": " + preAssignee.getRoleName() + "'</a>를   <a href='#'>'" + newAssignee.getUserId() + ": " + newAssignee.getRoleName() + "'</a>로   <a href='#'>수정</a>하였습니다.");
  }

  public <C extends Card, R extends Role, L extends CardList> String updateActivity(Object domain, String preData, String newData) {
    if (domain instanceof Card) {
      this.textOfDomain = "카드 <a href='#'>'" + preData + "'</a>를  <a href='#'>'" + newData + "'</a>로 <a href='#'>수정</a>하였습니다.";
      return startText.concat(textOfDomain);
    }

    if (domain instanceof Role) {
      this.textOfDomain = "역할 <a href='#'>'" + preData + "'</a>를  <a href='#'>'" + newData + "'</a>로 <a href='#'>수정</a>하였습니다.";
      return startText.concat(textOfDomain);
    }

    if (domain instanceof CardList) {
      this.textOfDomain = "리스트 <a href='#'>'" + preData + "'</a>를  <a href='#'>'" + newData + "'</a>로 <a href='#'>수정</a>하였습니다.";
      return startText.concat(textOfDomain);
    }
    return null;
  }
}
