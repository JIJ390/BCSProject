package edu.kh.bcs.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.bcs.admin.service.AdminService;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.myPage.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
	
	private static final int Device = 0;
	
	private final AdminService service; 
	
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
	public String adminBoard(
			Model model
			) {
//		휴대폰 정보 받아오기
		List<Device> result = service.deviceList();
		
		
		model.addAttribute("result", result);
		
		
		return "adminBoard";
		
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("adminMemberPage")
	public String adminMemberPage(
			@RequestParam("searchType") String searchType,
			@RequestParam("searchText") String searchText,
			@RequestParam("cp") int cp,
			Model model
			
		) {
		
		int resultCount = service.getResultCount(searchType, searchText);
		int cpCount = resultCount / 10;
		int cp1 = resultCount % 10;
		
		int pn1 = (cp -1) / 5;
		List<String> pnList = new ArrayList<>();
	
		
		for(int i = 1 + (pn1 * 5); i <= 5 + (pn1 * 5); i++) {
			if(i > cpCount+1) {
				break;
			}
			pnList.add(""+i);
		}
		
		if(cp1 != 0) {
			cpCount++;
		}

		System.out.println(cpCount);
		System.out.println(cpCount);
		System.out.println(pnList);
		System.out.println(pnList);
		
		model.addAttribute("pnList", pnList);
		model.addAttribute("cpCount", cpCount);
		
		for(int i = 0; i < pnList.size(); i++) {
			if(pnList.get(i).equals(""+cpCount)) {
				model.addAttribute("lastIndex", 1);
			}
			else {
				model.addAttribute("lastIndex",0);
			}
		}
		
		return "admin/adminMember/adminMemberPage";
	}
	
	
	@GetMapping("adminMemberSearch")
	public String adminMemberSearch(
			Model model,
			@RequestParam("searchType") String searchType,
			@RequestParam("searchText") String searchText,
			@RequestParam("cp") int cp,
			@RequestParam("ud") int ud
			) {
		
		List<Member> memberList = service.getMemberList(cp, searchType, searchText,ud);
		
		System.out.println(memberList.size());
		System.out.println(memberList.size());
		System.out.println(memberList.size());
		System.out.println(memberList.size());
		System.out.println(memberList.size());
		
		if(memberList.isEmpty()) {
			return "admin/adminMember/adminMemberListX";
		}

	
		model.addAttribute("memberList", memberList);
		
		return "admin/adminMember/adminMemberList";
	}
	
	
	
}
