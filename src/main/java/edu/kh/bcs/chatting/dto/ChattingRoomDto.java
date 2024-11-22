package edu.kh.bcs.chatting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChattingRoomDto {
	  private int chattingRoomNo;
	  private String chattingRoomCreateDate;
	  private int adminNo;
	  private int memberNo;
	  
	  private String memberName;
	  private String lastMessage;
	  
	  private int noReadCount;
}
