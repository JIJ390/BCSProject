package edu.kh.bcs.help.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.help.dto.EventDto;
import edu.kh.bcs.help.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Slf4j
public class EventController {
	
	private final EventService service;
	
	
	@GetMapping("help/eventWriteView")
	public String eventWriteGo() {
		return "/help/eventWrite";
	}
	
	

	@PostMapping("help/eventWrite")
	public String eventWrite(
			@ModelAttribute EventDto inputEvent,
			@RequestParam("eventImage2") MultipartFile eventImage2,
			RedirectAttributes ra
			) {
		
		System.out.println(inputEvent);
		
				
		// 서비스 호출 후 결과(작성된 게시글 번호) 반환받기
		int eventNo = service.eventWrite(inputEvent, eventImage2);
		
		
		// 3) 서비스 호출 후 결과(작성된 게시글 번호) 반환받기
		
		String path = null;
		String message = null;
		
		if(eventNo == 0) { // 실패
			path = "insert";
			message = "게시글 작성 실패";
		} else {
			path = "/help/event/" + eventNo; // 상세조회 주소
			message = "게시글이 작성 되었습니다";
		}
		
		ra.addFlashAttribute("message", message);
		
		
		return "redirect:" + path; // 임시 작성
		
	}

}
