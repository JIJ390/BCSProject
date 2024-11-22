package edu.kh.bcs.sse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.bcs.sse.mapper.SseMapper;

@Service
public class SseServiceImpl implements SseService{
	
	@Autowired
	private SseMapper mapper;

	@Override
	public int searchNewList(int memberNo) {
		return mapper.searchNewList(memberNo);
	}

}
