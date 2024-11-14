package edu.kh.bcs.help.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.bcs.help.dto.HelpDto;
import edu.kh.bcs.help.dto.NoticePagination;
import edu.kh.bcs.help.service.HelpService;
import edu.kh.bcs.main.MainController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("help")
public class HelpController {
	
	private final HelpService service;
	
	
	/* FAQ 버튼 누르면 FAQ 전환 */
	@GetMapping("faq")
	public String faq() {
		return "help/faq";
	}
	
	/* 공지사항 리스트 불러오기 */
	@GetMapping("notice")
	public String selectNoticeList(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model) {
		
		Map<String, Object> map = service.selectNoticeList(cp);
		
		List<HelpDto> noticeList = (List<HelpDto>)map.get("noticeList");
		NoticePagination pagination = (NoticePagination)map.get("pagination");
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pagination", pagination);
		
		log.debug("noticeList", noticeList);
		
		if(cp == 1) model.addAttribute("newMark", true);
		
		return "help/notice";
	}
	
	
	
	
}
