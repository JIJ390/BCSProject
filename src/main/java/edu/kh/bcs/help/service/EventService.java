package edu.kh.bcs.help.service;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.help.dto.EventDto;

public interface EventService {

	int eventWrite(EventDto inputEvent, MultipartFile eventImage);

}
