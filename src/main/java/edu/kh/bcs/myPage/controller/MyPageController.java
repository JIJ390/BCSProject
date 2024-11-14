package edu.kh.bcs.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.myPage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class MyPageController {
	
	private final MyPageService service;
	
	
	/**
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	@PostMapping("myPage/login")
	public String login(
			@RequestParam("memberId")String memberId,
			@RequestParam("memberPw")String memberPw,
			RedirectAttributes ra) {
		
//		log.debug("memberId : {}", memberId);
//		log.debug("memberPw : {}", memberPw);
		
		// 로그인 서비스 호출
		Member loginMember = service.login(memberId, memberPw);
		
		return "redirect:/"; // 메인 페이지 리다이렉트
	}
	
	
	

	@GetMapping("myPage/myPageLogin")
	public String myPageLogin() {
		
		return "myPage/myPageLogin";
	}
	
	
	@GetMapping("myPage/myPageMain")
	public String myPageMain() {
		
		return "myPage/myPageMain";
	}
	
	
	@GetMapping("myPage/myPageUpdate")
	public String myPageUpdate() {
		
		return "myPage/myPageupdate";
	}
	
	@GetMapping("myPage/myPageOrderHistory")
	public String myPageOrderHistory() {
		
		return "myPage/myPageOrderHistory";
	}
	
	@GetMapping("myPage/myPageSalesHistory")
	public String myPageSalesHistory() {
		
		return "myPage/myPageSalesHistory";
	}
	
	@GetMapping("myPage/myPagePointHistory")
	public String myPagePointHistory() {
		
		return "myPage/myPagePointHistory";
	}
	
	
	//
	
	
	
	
}
