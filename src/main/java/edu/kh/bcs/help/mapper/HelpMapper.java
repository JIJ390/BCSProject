package edu.kh.bcs.help.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.bcs.help.dto.HelpDto;

@Mapper
public interface HelpMapper {

	int getListCount();

	List<HelpDto> selectNoticeList(RowBounds rowBounds);

}
