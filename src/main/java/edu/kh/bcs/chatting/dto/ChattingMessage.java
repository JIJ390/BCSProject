package edu.kh.bcs.chatting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChattingMessage {
  private int messageNo;
  private String messageContent;
  private String readFl;
  private int senderNo;
  private int targetNo;
  private int chattingRoomNo;
  private String sendTime;
}
