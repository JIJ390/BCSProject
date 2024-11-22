package edu.kh.bcs.chatting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.mapper.ChattingMapper;

@Service
public class ChattingServiceImpl implements ChattingService{

	@Autowired
	private ChattingMapper mapper;
	
	@Override
	public int insertMessage(ChattingMessage msg) {
		return mapper.insertMessage(msg);
	}
	
	@Override
	public String getSenderName(int senderNo) {
		return mapper.getSenderName(senderNo);
	}
}
