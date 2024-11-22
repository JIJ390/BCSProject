package edu.kh.bcs.chatting.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.chatting.dto.ChattingMessage;

@Mapper
public interface ChattingMapper {

	int insertMessage(ChattingMessage msg);

	String getSenderName(int senderNo);

}
