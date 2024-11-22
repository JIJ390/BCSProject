package edu.kh.bcs.help.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.bcs.help.dto.HelpDto;

@Mapper
public interface HelpMapper {

	int getListCount();

	// 공지 전체 리스트
	List<HelpDto> selectNoticeList(RowBounds rowBounds);

	// 공지 자세히 보기
	HelpDto detailNoticeView(int noticeNo);
	
}
