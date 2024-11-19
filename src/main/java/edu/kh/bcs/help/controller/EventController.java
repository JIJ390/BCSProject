package edu.kh.bcs.help.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	/** 게시글 등록
	 * @param inputBoard : 제출된 key값이 일치하는 필드의 값이 저장된 객체(커맨드객체)
	 * @param loginMember : 로그인한 회원정보(글쓴이 회원번호 필요)
	 * @param images : 제출된 file 타입 input 태그 데이터
	 * @param ra : 리다이렉트시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("eventWrite")
	public String eventWrite(
			@ModelAttribute EventDto inputEvent,
			@RequestParam("eventImage") MultipartFile eventImage,
			RedirectAttributes ra
			) {
		
		System.out.println(inputEvent);
		
				
		// 서비스 호출 후 결과(작성된 게시글 번호) 반환받기
		int eventNo = service.eventWrite(inputEvent, eventImage);
		
		
		// 3) 서비스 호출 후 결과(작성된 게시글 번호) 반환받기
		
		
		return "redirect:boardMain";
	}

}
