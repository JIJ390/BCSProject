package edu.kh.bcs.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myPage")
public class MyPageController {

	@GetMapping("myPageLogin")
	public String project() {
		
		
		
		return "myPage/myPageLogin";
	}
	
	
	@GetMapping("signUpAccount")
	public String signUpAccount() {
		
		
		
		return "student/signUpAccount";
	}
	
	@GetMapping("signUpAccountConfirm")
	public String signUpAccountConfirm() {
	
		return "student/signUpAccountConfirm";
	}
	
	@GetMapping("signUpCreate")
	public String signUpCreate() {
		
		return "student/signUpCreate";
	}
	
}
