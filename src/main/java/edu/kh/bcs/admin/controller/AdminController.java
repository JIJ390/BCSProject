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
import org.springframework.web.bind.annotation.PathVariable;
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
import edu.kh.bcs.device.dto.Capacity;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.Grade;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.SellingDevice;
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
	@PostMapping("adminBrandFilter")
	public String brandFilter(
			@RequestBody String brandFilter,
			Device device,
			Color color,
			Model model
			) {
		
		
		
		List<Device> brandFilter1 = service.brandFilter(brandFilter);
		
		
		model.addAttribute("brandFilter", brandFilter1);
		
		log.debug("asdasdadad : {}", brandFilter1);
		
		return "admin/adminProducktinquiryList";
	}
	
	
	
	@GetMapping("adminChatList")
	public String adminChatList() {
		
		
		return "admin/adminChatList";
	}
	
	//구매 신청 
	@GetMapping("adminSale/{deviceNo}")
	public String adminSale(
			Model model,
			@PathVariable("deviceNo") String deviceNo
			) {
		
		log.debug("controller device 값 : {}", deviceNo);
		log.debug("controller device 값 : {}", deviceNo);
		log.debug("controller device 값 : {}", deviceNo);
		
		
		log.debug("controller device split 값 : {}", deviceNo);
		log.debug("controller device split 값 : {}", deviceNo);
		log.debug("controller device split 값 : {}", deviceNo);
		
		
		
		//리스트 조
		List<Order> listView = service.adminSale(deviceNo);
		
		
		log.debug("결과 값 : {}", listView);
		log.debug("결과 값 : {}", listView);
		log.debug("결과 값 : {}", listView);
		model.addAttribute("listView", listView);
		
		
		return "admin/adminSale";
	}
	
	@ResponseBody
	@PostMapping("/delivery")
	public int delivery(
			@RequestBody Map<String, Integer> map
			) {
		
		int orderNo = map.get("orderNo");
		int orderStatusCode = map.get("orderStatusCode");
		
		
		
		log.debug("orderNoorderNoorderNo : {}", orderNo);
		
		log.debug("orderStatusCode : {}", orderStatusCode);
		
		
		
		
		//상태 업데이트
		int delivery = service.delivery(orderNo,orderStatusCode);
		System.out.println("고객정보 업데이트 완료");
		return delivery;
	}
	
	@PostMapping("/serachFilter")
	public String serachFilter(
			@RequestBody String searchResult,
			Model model
			) {
		
		//구매 신청 목록 검색기능필터
		List<Order> result = service.serachFilter(searchResult);
		
		log.debug("asdasdasdasd : {}", result);
		log.debug("asdasdasdasd : {}", result);
		log.debug("asdasdasdasd : {}", result);
		log.debug("asdasdasdasd : {}", result);
		log.debug("asdasdasdasd : {}", result);
		
		model.addAttribute("result", result);
		
		
		return "admin/saleList";
	}
	
	
	
	
	@GetMapping("androidPopUp")
	public String androidPopUp() { 
		
		
		return "admin/androidPopUp";
	}
	
	
	@GetMapping("adminSale")
	public String adminSaleFirst(
			Model model
			) { 
		
		
		List<Order> result = service.adminSaleFirst();
		
		
		model.addAttribute("listView", result);
		
		
		return "admin/adminSale";
		
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
	        @RequestParam("capacityNumber") String capacityNumber,
	        @RequestParam("capacityPrice") String capacityPrice,
	        @RequestParam("capacitySellPrice") String capacitySellPrice,
	        RedirectAttributes rs
			) {
		
		
		
		
		//divce 객체로 넣어줄거 
		// gradeSellPrice, gradeSellPrice dto 인트라서 한번에 안얻어져와 String으로 requestParam으로 받음
		int text = service.textContent(device,color,gradeType,gradePrice,gradeSellPrice,
				colorImg,divceImg,capacityNumber,capacityPrice,capacitySellPrice);
		
		return "redirect:/admin/adminProductinquiry";
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
		
		System.out.println(deviceList);
		System.out.println(deviceList);
		System.out.println(deviceList);
		System.out.println(deviceList);
		
		return "admin/adminEvent/deviceType";
	}
	
	
	
	
	
	
	
}
