package edu.kh.bcs.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myPage")
public class MyPageController {

	@GetMapping("myPageLogin")
	public String myPageLogin() {
		
		
		
		return "myPage/myPageLogin";
	}
	
	
	@GetMapping("myPageMain")
	public String myPageMain() {
			
		
		
		return "myPage/myPageMain";
	}
	
	
	@GetMapping("myPageUpdate")
	public String myPageUpdate() {
		
		return "myPage/myPageupdate";
	}
	
	@GetMapping("myPageOrderHistory")
	public String myPageOrderHistory() {
		
		return "myPage/myPageOrderHistory";
	}
	
	@GetMapping("myPageSalesHistory")
	public String myPageSalesHistory() {
		
		return "myPage/myPageSalesHistory";
	}
	
	@GetMapping("myPagePointHistory")
	public String myPagePointHistory() {
		
		return "myPage/myPagePointHistory";
	}
	
}
