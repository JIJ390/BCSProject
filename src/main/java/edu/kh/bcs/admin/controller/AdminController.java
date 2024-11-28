
package edu.kh.bcs.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;


import edu.kh.bcs.admin.service.AdminService;

import edu.kh.bcs.common.util.FileUtil;

import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.Grade;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.help.dto.EventDto;
import edu.kh.bcs.help.dto.MainBannerDto;
import edu.kh.bcs.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes("loginMember")
@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
@Slf4j
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
		List<Device> boardList = service.deviceList();
		model.addAttribute("boardList", boardList);
		
		
		
		return "admin/adminBoard";
		
		
	}
	
	@GetMapping("/adminBoard/search")
	public String search(
			Model model,
			@RequestParam(value = "search", required=false) String search
			) {
		
		
		
		List<Device> result = service.adminSearch(search);
		
		
		
		model.addAttribute("boardList", result);
		
		return "admin/adminBoard";
	}
	

	
	
	//배송 내역 조회
	@GetMapping("delivery")
	public String delivery() {
		
		
		return "admin/delivery";
	}
	
	
	//이벤트 관리
	@GetMapping("adminMainBanner")  
	public String adminBanner() {
		
		
		return "admin/adminMainBanner";
	}
	//이벤트 관리
	@GetMapping("adminEvent")  
	public String adminEvent() {
		
		
		return "admin/adminEvent";
	}
	
	@GetMapping("adminProductinquiry")
	public String adminProductinquiry(
			Device device,
			Color color,
			Model model
			) {
		
		List<Device> result = service.result(device,color);
		
		
		model.addAttribute("deviceList", result);
		
		
		return "admin/adminProductinquiry";
	}
	
	
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
	
	@PostMapping("/adminModelRegistration/insert")
	public String insertDevice(
			//메인사진
	        @RequestParam(name = "divceImg", required = false) MultipartFile  divceImg,
	        //6개 사진 담겨있음
	        @RequestParam("colorImg") List<MultipartFile> colorImg,
	        @ModelAttribute Color color,
	        @ModelAttribute Device device,
	        @RequestParam("gradePrice") String gradePrice,
	        @RequestParam("gradeSellPrice") String gradeSellPrice,
	        @RequestParam("gradeType") String gradeType,
	        RedirectAttributes rs
			) {
		
		
		//divce 객체로 넣어줄거 
		// gradeSellPrice, gradeSellPrice dto 인트라서 한번에 안얻어져와 String으로 requestParam으로 받음
		int text = service.textContent(device,color,gradeType,gradePrice,gradeSellPrice,colorImg,divceImg);
		
		return "redirect:/admin/adminBoard";
	}
	
	
//	기종 등록
	@GetMapping("adminModelRegistration")
	public String adminModelRegistration() {
		
		
		return "admin/adminModelRegistration ";
	}
	
	// SELLING 이 아니라  DEVICE로 해야할듯
	@PostMapping("/popUpData")
	public String popUpData(
			@RequestBody int result,
			Model model
			) {
		
		
		
		
		
		List<Color> popUpData = service.popUpData(result);
		
		
		
		
		model.addAttribute("popUpData", popUpData);
		
		return "admin/androidPopUp :: popUp-tbody";
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
	
	
	@GetMapping("getEventPagenation")
	public String adminEventPage(
			Model model,
			@RequestParam("cp") int cp
			) {
		
		int resultCount = service.geteventListCount();
		
		int cpCount = resultCount / 6;
		int cp1 = resultCount % 6;
		
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
	
	@GetMapping("getEventList")
	public String getEventList(
			@RequestParam("cp") int cp,
			Model model
			) {
		
		List<EventDto> eventList = service.getEventList(cp);
				
		model.addAttribute("eventList",eventList);
		System.out.println(eventList);
		System.out.println(eventList);
		System.out.println(eventList);
		
		return "admin/adminEvent/eventList";
	}
	
	@ResponseBody
	@PostMapping("eventImgUpdate")
	public int eventImgUpdate(
			@RequestParam("img") MultipartFile img,
			@RequestParam("eventNo") int eventNo
			) {
		
		int result = service.eventImgUpdate(img,eventNo);
		
		return result;
	}
	@ResponseBody
	@PostMapping("eventTitleUpdate")
	public int eventTitleUpdate(
			@RequestParam("eventTitle") String eventTitle,
			@RequestParam("eventNo") int eventNo
			) {
		
		int result = service.eventTitleUpdate(eventTitle,eventNo);
		
		return result;
	}
	@ResponseBody
	@PostMapping("eventContentUpdate")
	public int eventContentUpdate(
			@RequestParam("eventContent") String eventContent,
			@RequestParam("eventNo") int eventNo
			) {
		
		int result = service.eventContentUpdate(eventContent,eventNo);
		
		return result;
	}
	@ResponseBody
	@PostMapping("eventFlUpdate")
	public String eventFlUpdate(
			@RequestParam("eventNo") int eventNo
			) {
		
		String result = service.eventFlUpdate(eventNo);
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("adminMemberSearch")
	public String adminMemberSearch(
			Model model,
			@RequestParam("searchType") String searchType,
			@RequestParam("searchText") String searchText,
			@RequestParam("cp") int cp,
			@RequestParam("ud") int ud,
			@RequestParam("searchAsc") String searchAsc
			) {
		
		List<Member> memberList = service.getMemberList(cp, searchType, searchText,ud, searchAsc);
		


		
		if(memberList.isEmpty()) {
			return "admin/adminMember/adminMemberListX";
		}

		
		model.addAttribute("memberList", memberList);
		
		return "admin/adminMember/adminMemberList";
	}
	
	@ResponseBody
	@GetMapping("adminMemberDetail")
	public Map<String, String> adminMemberDetail(
			@RequestParam("memberNo") int memberNo
			) {
		
		Map<String , String> map = service.adminMemberDetail(memberNo);
		return map;
	}
	
	@ResponseBody
	@GetMapping("memberDelFlChange")
	public int memberDelFlChange(
			@RequestParam("memberNo") int memberNo
			) {
		
		return service.memberDelFlChange(memberNo);
	}
	@ResponseBody
	@GetMapping("memberFlChange")
	public int memberFlChange(
			@RequestParam("memberNo") int memberNo
			) {
		
		return service.memberFlChange(memberNo);
	}
	
	@GetMapping("memberLogin")
	public String memberLogin(
			@RequestParam("memberNo") int memberNo,
			Model model
			) {
		
		Member loginMember = service.getLoginMember(memberNo);

		
		model.addAttribute("loginMember", loginMember);
		
		return "admin/memberManage";
	}
	
	// galaxy 시리즈
	@GetMapping("event/galaxy")
	public String galaxy(
			Model model,
			@RequestParam("series") String series
			) {
		
		List<Device> deviceList = service.galaxyA(series);
		
		model.addAttribute("deviceList", deviceList);
		
		return "admin/adminEvent/deviceType";
	}
	// iPhone 시리즈
	@GetMapping("event/iPhone")
	public String iPhone(
			Model model,
			@RequestParam("series") String series
			) {
		
		List<Device> deviceList = service.iPhone(series);
		
		model.addAttribute("deviceList", deviceList);
		
		
		return "admin/adminEvent/deviceType";
	}
	
	@GetMapping("eventList")
	public String eventList(
			Model model
			) {
		
		List<EventDto> eventList = service.getEventList();
		
		model.addAttribute("eventList", eventList);
		
		return "/admin/adminEvent/bannerEvent";
	}
	
	@ResponseBody
	@PostMapping("mainBannerInsert")
	public int mainBannerInsert(
			@RequestParam("image1") MultipartFile file1,
			@RequestParam("title1") String title1,
			@RequestParam("content1") String content1,
			@RequestParam("color1") String color1,
			@RequestParam("lr1") String lr1,
			@RequestParam("linkNo1") String linkNo1,
			@RequestParam("image2") MultipartFile file2,
			@RequestParam("title2") String title2,
			@RequestParam("content2") String content2,
			@RequestParam("color2") String color2,
			@RequestParam("lr2") String lr2,
			@RequestParam("linkNo2") String linkNo2,
			@RequestParam("image3") MultipartFile file3,
			@RequestParam("title3") String title3,
			@RequestParam("content3") String content3,
			@RequestParam("color3") String color3,
			@RequestParam("lr3") String lr3,
			@RequestParam("linkNo3") String linkNo3,
			@RequestParam("image4") MultipartFile file4,
			@RequestParam("title4") String title4,
			@RequestParam("content4") String content4,
			@RequestParam("color4") String color4,
			@RequestParam("lr4") String lr4,
			@RequestParam("linkNo4") String linkNo4
			) {
		
		MainBannerDto banner1 = new MainBannerDto();
		banner1.setMainBannerTitle(title1);
		banner1.setMainBannerContent(content1);
		banner1.setMainBannerFontColor(color1);
		banner1.setMainBannerLr(lr1);
		banner1.setMainBannerLink(linkNo1);
		
		MainBannerDto banner2 = new MainBannerDto();
		banner2.setMainBannerTitle(title2);
		banner2.setMainBannerContent(content2);
		banner2.setMainBannerFontColor(color2);
		banner2.setMainBannerLr(lr2);
		banner2.setMainBannerLink(linkNo2);
		
		MainBannerDto banner3 = new MainBannerDto();
		banner3.setMainBannerTitle(title3);
		banner3.setMainBannerContent(content3);
		banner3.setMainBannerFontColor(color3);
		banner3.setMainBannerLr(lr3);
		banner3.setMainBannerLink(linkNo3);
		
		MainBannerDto banner4 = new MainBannerDto();
		banner4.setMainBannerTitle(title4);
		banner4.setMainBannerContent(content4);
		banner4.setMainBannerFontColor(color4);
		banner4.setMainBannerLr(lr4);
		banner4.setMainBannerLink(linkNo4);
		
		int result = service.updateBanner(banner1,banner2,banner3,banner4,file1,file2,file3,file4);
		
		System.out.println(banner1);
		System.out.println(banner2);
		System.out.println(banner3);
		System.out.println(banner4);
		
		return result;
	}
	
	
	
	
	
}
