package edu.kh.bcs.help.service;

import java.util.Map;

import edu.kh.bcs.help.dto.HelpDto;

public interface HelpService {

	Map<String, Object> selectNoticeList(int cp);

	HelpDto detailViewNotice(int noticeNo);

}
