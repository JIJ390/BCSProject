package edu.kh.bcs.chatting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChattingRoomDto {
	  private int chattingRoomNo;
	  private String lastMessage;
	  private String sendTime;
	  private int targetNo;
	  private String targetNickname;
	  private String targetProfile;
	  private int notReadCount;
	  private int maxMessageNo;
}
