package edu.kh.bcs.chatting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChattingMessage {
  private int chattingMessageNo;
  private String chattingMessageContent;
  private String chattingMessageReadFl;
  private int senderNo;
  private String senderName;
  private int receiverNo;
  private int chattingRoomNo;
  private String chattingMessageSendDate;
}
