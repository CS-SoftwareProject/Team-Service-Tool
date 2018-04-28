package TT.domain.cardlist;

import java.util.ArrayList;
import java.util.List;

import TT.domain.card.Card;

public class CardList {

  private int listNum;
  private int boardNum;
  private String listName;
  private int listOrder;
  private List<Card> cards = new ArrayList<Card>();

  public CardList() {
  }

  public CardList(int boardNum,String listName,int listOrder){
    this.boardNum=boardNum;
    this.listName=listName;
    this.listOrder=listOrder;
  }

  public CardList(int borardNum, int listOrder) {
    this.boardNum=borardNum;
    this.listOrder=listOrder;
  }

  public CardList(int listNum, int boardNum, String listName, int listOrder) {
    this.listNum=listNum;
    this.boardNum=boardNum;
    this.listName=listName;
    this.listOrder=listOrder;
  }

  public CardList(CardList list) {
    this.boardNum=list.boardNum;
    this.listName=list.listName;
    this.listNum=list.listNum;
    this.listOrder=list.listOrder;
  }

  public int getListNum() {
    return listNum;
  }

  public void setListNum(int listNum) {
    this.listNum = listNum;
  }

  public int getBoardNum() {
    return boardNum;
  }

  public void setBoardNum(int boardNum) {
    this.boardNum = boardNum;
  }

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public int getListOrder() {
    return listOrder;
  }

  public void setListOrder(int listOrder) {
    this.listOrder = listOrder;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  @Override
  public String toString() {
    return "CardList [listNum=" + listNum + ", boardNum=" + boardNum + ", listName=" + listName + ", listOrder=" + listOrder + ", cards=" + cards + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + boardNum;
    result = prime * result + ((cards == null) ? 0 : cards.hashCode());
    result = prime * result + ((listName == null) ? 0 : listName.hashCode());
    result = prime * result + listNum;
    result = prime * result + listOrder;
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
    CardList other = (CardList) obj;
    if (boardNum != other.boardNum)
      return false;
    if (cards == null) {
      if (other.cards != null)
        return false;
    } else if (!cards.equals(other.cards))
      return false;
    if (listName == null) {
      if (other.listName != null)
        return false;
    } else if (!listName.equals(other.listName))
      return false;
    if (listNum != other.listNum)
      return false;
    if (listOrder != other.listOrder)
      return false;
    return true;
  }

}
