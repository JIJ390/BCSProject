package edu.kh.bcs.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	// localhost/admin 접속 시 admin/adminMain.html 매핑
	@RequestMapping("")
	public String adminMain() {
		return "admin/admin";
	}
	
	@GetMapping("memberManage")
	public String getMethodName() {
		return "admin/memberManage";
	}
	
	//게시글 관리
	@GetMapping("adminBoard")
	public String adminBoard() {
		
		
		return "admin/adminBoard";
	}
	
	
	//배송 내역 조회
	@GetMapping("delivery")
	public String delivery() {
		
		
		return "admin/delivery";
	}
	
	
	//이벤트 관리
	@GetMapping("adminEvent")  
	public String adminEvent() {
		
		
		return "admin/adminEvent";
	}
	
	//1:1 상담 목록
	@GetMapping("adminChatList")
	public String adminChatList() {
		
		
		return "admin/adminChatList";
	}
	
	//구매 신청 
	@GetMapping("adminSale")
	public String adminSale() {
		
		
		return "admin/adminSale";
	}
	
	@GetMapping("androidPopUp")
	public String androidPopUp() { 
		
		
		return "admin/androidPopUp";
	}
	
	
	//매물 등록
	@GetMapping("adminRegistration")
	public String adminRegistration() {
		
		
		return "admin/adminRegistration";
	}
	
//	기종 등록
	@GetMapping("adminModelRegistration")
	public String adminModelRegistration() {
		
		
		return "admin/adminModelRegistration ";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
