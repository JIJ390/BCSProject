package edu.kh.bcs.help.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.help.dto.EventDto;

public interface EventService {

	int eventWrite(EventDto inputEvent, MultipartFile eventImage);

	Map<String, Object> selectEventList(int cp);

	EventDto detailViewEvent(int eventNo);

}
