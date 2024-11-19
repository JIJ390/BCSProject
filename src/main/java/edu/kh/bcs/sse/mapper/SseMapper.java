package edu.kh.bcs.sse.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SseMapper {

	public int searchNewList(int memberNo);

}
