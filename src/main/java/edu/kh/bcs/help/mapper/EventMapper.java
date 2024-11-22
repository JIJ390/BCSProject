package edu.kh.bcs.help.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.bcs.help.dto.EventDto;


@Mapper
public interface EventMapper {

	int eventWrite(EventDto inputEvent);

	List<EventDto> selectEventList(RowBounds rowBounds);

	int getListCount();
	


}
