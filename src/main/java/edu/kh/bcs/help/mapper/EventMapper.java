package edu.kh.bcs.help.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.help.dto.EventDto;


@Mapper
public interface EventMapper {

	int eventWrite(EventDto inputEvent);
	


}
