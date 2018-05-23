package TT.domain.message;

import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Message {
  private int msgNum;
  private String sender;
  @NotNull(message = "받는 사람을 입력하세요.")
  @Size(min = 4, max = 12)
  private String receiver;
  @NotNull(message = "제목을 입력하세요.")
  @Size(min = 1, max = 99)
  private String subject;
  @NotNull(message = "내용을 입력하세요.")
  @Size(min = 1, max = 999)
  private String content;
  private Date date;
  private int readed;
  private String dateDiff;

  public Message() {

  }

  public Message(int msgNum, String sender, String receiver, String subject, String content, Date sendTime, int readed) {
    this.msgNum = msgNum;
    this.sender = sender;
    this.receiver = receiver;
    this.subject = subject;
    this.content = content;
    this.date = sendTime;
    this.readed = readed;
  }
  
  public Message(String sender, String receiver, String subject, String content) {
    this.sender = sender;
    this.receiver = receiver;
    this.subject = subject;
    this.content = content;
  }

  public int getMsgNum() {
    return msgNum;
  }

  public void setMsgNum(int msgNum) {
    this.msgNum = msgNum;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getReaded() {
    return readed;
  }

  public void setReaded(int readed) {
    this.readed = readed;
  }
  
  public String getDateDiff() {
    return dateDiff;
  }

  public void setDateDiff(String dateDiff) {
    this.dateDiff = dateDiff;
  }

  @Override
  public String toString() {
    return "Message [msgNum=" + msgNum + ", sender=" + sender + ", receiver=" + receiver + ", subject=" + subject + ", content=" + content + ", date=" + date + ", readed=" + readed + ", dateDiff=" + dateDiff + "]";
  }
}
