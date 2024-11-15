package edu.kh.bcs.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	 * @param ra : 리다이렉트시 request scope로 값 전달하는 객체
	 * @param : 데이터 전달용 객체(기본값 request scope)
	 */
	@PostMapping("myPage/login")
	public String login(
			@RequestParam("memberId")String memberId,
			@RequestParam("memberPw")String memberPw,
			RedirectAttributes ra,
			Model model) {
		
		log.debug("memberId : {}", memberId);
		log.debug("memberId : {}", memberId);
		log.debug("memberId : {}", memberId);
		log.debug("memberId : {}", memberId);
		log.debug("memberPw : {}", memberPw);
		
		// 로그인 서비스 호출
		Member loginMember = service.login(memberId, memberPw);
		
		log.debug("loginMember {}", loginMember);
		log.debug("loginMember {}", loginMember);
		log.debug("loginMember {}", loginMember);
		log.debug("loginMember {}", loginMember);
		
		
		if(loginMember == null) { // 로그인이 실패
			
			log.debug("aaaaaaaaaa");
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다!");
		}else { // 로그인 성공
			
			// loginMember를 session scope에 추가
			// 방법 1) HttpSession 이용
			// 방법 2) @SessionAttributes + Model 이용방법
			
			/* Model을 이용해서 Session scope에 값 추가하는 방법 */
			// 1. model에 값 추가 (request)
			model.addAttribute("loginMember", loginMember);
			
			log.debug("loginMember", loginMember);
			
			// 2. 클래스 선언부에 @SessionAttributes({"key"}) 추가
			// -> key 값은 model에 추가된 값 "loginMember" 작성
			// (request -> session) 으로 바뀜
			
			// @SessionAttributes :
			// Model 에 추가된 값 중 session scope로 올리고 싶은 값의
			// key를 작성하는 어노테이션
		}
		
		
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
