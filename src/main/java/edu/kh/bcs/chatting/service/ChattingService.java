package edu.kh.bcs.chatting.service;

import edu.kh.bcs.chatting.dto.ChattingMessage;

public interface ChattingService {

	int insertMessage(ChattingMessage msg);

	String getSenderName(int senderNo);

}
