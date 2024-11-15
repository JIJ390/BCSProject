package edu.kh.bcs.help.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.bcs.help.dto.HelpDto;
import edu.kh.bcs.help.dto.NoticePagination;
import edu.kh.bcs.help.mapper.HelpMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HelpServiceImpl implements HelpService {
	
	private final HelpMapper mapper;
	
	@Override
		public Map<String, Object> selectNoticeList(int cp) {
		
		int listCount = mapper.getListCount();
		
		NoticePagination pagination = new NoticePagination(cp, listCount);
		int limit = pagination.getLimit();
		int offset = (cp - 1)* limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);

		List<HelpDto> noticeList = mapper.selectNoticeList(rowBounds);
		
		Map<String, Object> map = 
							Map.of("noticeList", noticeList, "pagination", pagination);
		
			return map;
		}
	
	

}
