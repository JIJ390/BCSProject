package edu.kh.bcs.signUp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.signUp.service.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("signUp")
@RequiredArgsConstructor
public class SignUpController {
	
	public final SignUpService service;
	
	

	/**
	 * 회원가입 누르는 페이지
	 * @return
	 */
	@GetMapping("signUp")
	public String signUp() {
		
		
		return "signUp/signUp";
	}
	
	@GetMapping("signUpAccount")
	public String signUpAccount() {
		
		return "signUp/signUpAccount";
	}
	
	@GetMapping("signUpAccountConfirm")
	public String signUpAccountConfirm() {
	
		return "signUp/signUpAccountConfirm";
	}
	
	@GetMapping("signUpCreate")
	public String signUpCreate() {
		
		return "signUp/signUpCreate";
	}
	
	/** 이메일 중복 검사(비동기)
	 * @return email : 입력된 이메일
	 * @return 0 중복 X, 1: 중복 O
	 */
	@ResponseBody // 반환값을 응답 본문 (ajax 코드)로 반환
	@GetMapping("emailCheck")
	public int emailCheck(
			@RequestParam("email") String email) {
		
		return service.emailCheck(email);
	}
	
	/** 아이디 중복검사(비동기)
	 * @param id
	 * @return 0 : 중복 X, 1 : 중복 O
	 */
	@ResponseBody
	@GetMapping("idCheck")
	public int idCheck(
			@RequestParam("id") String id) {
		
		return service.idCheck(id);
	}
	

	/** 회원가입 수행
	 * @param inputMember : 입력값이 저장된 Member 객체(커맨드 객체)
	 * @param ra : 리다이렉트 시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("signUpCreate")
	public String signUpRun(
			@ModelAttribute Member inputMember,
			RedirectAttributes ra) {
	
		// 회원가입 서비스 호출
		int result = service.signUpRun(inputMember);
		
		log.debug("회원가입정보 : {}", inputMember);
		
		
		// 서비스 결과에 따라 응답 제어
		String path = null;
		String message = null;
		
		if(result > 0) {
			path = "/";
			message
			 	= inputMember.getMemberId() + "님의 가입을 환영합니다^^";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	
	
	
	
}
