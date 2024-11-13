package edu.kh.bcs.signUp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.bcs.signUp.service.SignUpService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("signUp")
@RequiredArgsConstructor
public class SignUpController {
	
	public final SignUpService service;
	

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
	
	
	
	
	
	
	
	
}
