
package edu.kh.bcs.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import org.springframework.web.bind.annotation.SessionAttributes;


import edu.kh.bcs.admin.service.AdminService;
import edu.kh.bcs.common.dto.Pagination;
import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.device.service.DeviceSellingService;
import edu.kh.bcs.help.dto.EventDto;
import edu.kh.bcs.help.dto.HelpDto;
import edu.kh.bcs.help.dto.MainBannerDto;
import edu.kh.bcs.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes("loginMember")
@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
	
	private static final int Device = 0;
	
	private final AdminService service; 
	
	private final DeviceSellingService sellingService;
	
	// localhost/admin 접속 시 admin/adminMain.html 매핑
	@RequestMapping("")
	public String adminMain(
			Model model) {
		
		// 연간 매매금액
		List<Map<String, String>> annualTotalSales = service.selectAnnualTotalSales();
		
		// 연간 매입 금액
		List<Map<String, String>> annualTotalPurchases = service.selectAnnualTotalPurchases();
		
		model.addAttribute("annualTotalSales", annualTotalSales);
		model.addAttribute("annualTotalPurchases", annualTotalPurchases);
		
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
		
		
		//리스트 조
		List<Order> listView = service.adminSale(deviceNo);
		
		
		log.debug("결과 값 : {}", listView);
		log.debug("결과 값 : {}", listView);
		log.debug("결과 값 : {}", listView);
		model.addAttribute("listView", listView);
		
		
		return "admin/adminSale";
	}
	
	
	
	// 판매 신청
	@GetMapping("adminBuy/{deviceNo}")
	public String adminBuy(
			Model model,
			@PathVariable("deviceNo") String deviceNo
			) {
		
		model.addAttribute("deviceNo", deviceNo);
		
		return "admin/adminBuy";
	}
	
	
	
	@GetMapping("adminBuy/searchDevice")
	public String searchDevice(
			@RequestParam("deviceNo") String deviceNo,
			@RequestParam("cp") int cp,
			@RequestParam("searchText") String searchText,
			Model model
			) {
		
		List<SellingDevice> deviceList = service.getBuyingList(deviceNo,cp,searchText);
		
		model.addAttribute("deviceList",deviceList);
		
		return "admin/adminSelling/selling";
	}
	
	@GetMapping("adminBuy/searchDevicePage")
	public String searchDevicePage(
			@RequestParam("deviceNo") String deviceNo,
			@RequestParam("cp") int cp,
			@RequestParam("searchText") String searchText,
			Model model
			) {
		
		// 조건에 맞게 검색 후 개수 조회
		int resultCount = service.getDeviceResultCount(deviceNo,searchText);
		
		
		
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
	
	
	
	
	
	
	@ResponseBody
	@PostMapping("adminBuy/updateStatue")
	public int updateStatus(
			@RequestBody Map<String, String> map
			) {

		int result 
			= service.updateStatue(map.get("sellingDeviceNo"), map.get("statusCode"));
		
		return result;
	}
	@ResponseBody
	@PostMapping("adminBuy/addBuyDevice")
	public int addBuyDevice(
			@RequestBody Map<String, String> map
			) {
		
		String deviceNo = map.get("deviceNo");
		String colorNo = map.get("colorNo");
		String capacityNumber = map.get("capacityNumber");
		String gradeNumber = map.get("gradeNumber");
		String orderNo = map.get("orderNo");
		
		int result = service.addBuyDevice(deviceNo,colorNo,capacityNumber,gradeNumber,orderNo);
		
		return result;
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
		
		model.addAttribute("result", result);
		
		
		return "admin/saleList";
	}
	
	
	
	
	@GetMapping("androidPopUp")
	public String androidPopUp() { 
		
		
		return "admin/androidPopUp";
	}
	
	
	@GetMapping("adminAllList")
	public String adminAllList(
			Model model
			) { 
		
		
		List<BuyingDevice> buyingDeviceList = (List<BuyingDevice>)service.selectBuyingDeviceList();
		
		
		model.addAttribute("buyingDeviceList", buyingDeviceList);
		
		
		return "admin/adminAllList";
		
	}
	
	@GetMapping("adminAllList/search")
	public String adminAllList(
			@RequestParam(name = "search", required = false) String search,
			Model model
			) {
		
		List<BuyingDevice> adminAllListSearch = (List<BuyingDevice>)service.adminAllListSearch(search);
		
		
		model.addAttribute("buyingDeviceList", adminAllListSearch);
		
		return "admin/adminAllList";
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
		
		
		String[] capacityNo1 = capacityNumber.split(",");
		String[] capacityPrice1 = capacityPrice.split(",");
		String[] capacitySellPrice1 = capacitySellPrice.split(",");
		
		System.out.println(capacityNo1.toString());
		System.out.println(capacityPrice1.toString());
		System.out.println(capacitySellPrice1.toString());
		System.out.println(capacityNo1.length);
		System.out.println(capacityPrice1.length);
		System.out.println(capacitySellPrice1.length);
		
		log.debug("capacityPrice : {}", capacityPrice);
		
		//divce 객체로 넣어줄거 
		// gradeSellPrice, gradeSellPrice dto 인트라서 한번에 안얻어져와 String으로 requestParam으로 받음
		int text = service.textContent(device,color,gradeType,gradePrice,gradeSellPrice,
				colorImg,divceImg,capacityNumber,capacityPrice,capacitySellPrice);
		
		return "redirect:/admin/adminProductinquiry";
	}
	
	
	
	@PostMapping("adminUpdate/{deviceNo}")
	public String deviceUpdate(
			//메인사진
	        @RequestParam(name = "divceImg", required = false) MultipartFile  divceImg,
	        //6개 사진 담겨있음
	        @RequestParam(name = "colorImg", required = false) List<MultipartFile> colorImg,
	        @ModelAttribute Color color,
	        @ModelAttribute Device device,
	        @RequestParam("gradePrice") String gradePrice,
	        @RequestParam("gradeSellPrice") String gradeSellPrice,
	        @RequestParam("gradeType") String gradeType,
	        @RequestParam("capacityNumber") String capacityNumber,
	        @RequestParam("capacityPrice") String capacityPrice,
	        @RequestParam("capacitySellPrice") String capacitySellPrice,
	        @RequestParam("deviceNo") String deviceNo,
	        @RequestParam("colorStatus") String colorStatus,
	        @RequestParam("colorNoCode") String colorNoCode,
	        RedirectAttributes rs,
	        Model model
			) {
	
		
		//divce 객체로 넣어줄거 
		// gradeSellPrice, gradeSellPrice dto 인트라서 한번에 안얻어져와 String으로 requestParam으로 받음
		int text = service.textContentUpdate(device,color,gradeType,gradePrice,gradeSellPrice,
				colorImg,divceImg,capacityNumber,capacityPrice,capacitySellPrice, colorStatus, colorNoCode);
		
		
		return "/admin/adminProductinquiry";
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
	
		if(cp1 != 0) {
			cpCount++;
		}
		for(int i = 1 + (pn1 * 5); i <= 5 + (pn1 * 5); i++) {
			if(i > cpCount) {
				break;
			}
			pnList.add(""+i);
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
	
		if(cp1 != 0) {
			cpCount++;
		}
		for(int i = 1 + (pn1 * 5); i <= 5 + (pn1 * 5); i++) {
			if(i > cpCount) {
				break;
			}
			pnList.add(""+i);
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
	

	//update 화면 전환
	@GetMapping("adminUpdate/{deviceNo}")
	public String update(
			Model model,
			@PathVariable("deviceNo") String deviceNo
			) {
		
//		log.debug("deviceNo : {}", deviceNo);
		Map<String, Object> reload = service.reload(deviceNo);
		
		
		reload.get("device");
		reload.get("grade");
		reload.get("color");
		reload.get("capacityPrice");
		
		
		
		
		
		
		log.debug("device : {}", reload.get("device"));
		log.debug("grade : {}", reload.get("grade").getClass().getName());
		log.debug("color : {}", reload.get("color"));
		log.debug("capacityPrice : {}", reload.get("capacityPrice"));
		
		
		List<Color>	colorList = (List<Color>)reload.get("color");
		
		int colorLength = colorList.size();
		
		log.debug("colorLength : {}", colorLength);
		
		for(int i = colorLength; i < 6; i++) {
			colorList.add(null);
		}
		
		
		model.addAttribute("device", reload.get("device"));
		model.addAttribute("grade", reload.get("grade"));
		model.addAttribute("colorResultList", colorList);
		model.addAttribute("capacityPrice", reload.get("capacityPrice"));
		
		
		
		return "admin/adminUpdate";
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
		
		return result;
	}


	@GetMapping("adminProductinquiry/search")
	public String adminProductinquiry(
			Model model,
			@RequestParam("search") String search
			) {
		
		List<Device> ListView = service.productinquirySearch(search);
		model.addAttribute("deviceList", ListView);
		
		return "admin/adminProductinquiry";
	}
	

	@GetMapping("modelSelect/{brandName}")
	public String modelSelect(
			@PathVariable("brandName") String brandName
			, Model model) {
		
		List<Device> deviceList = service.modelSelect(brandName);
		
//		System.out.println(deviceList);
//		System.out.println(deviceList);
//		System.out.println(deviceList);
//		System.out.println(deviceList);
//		System.out.println(deviceList);
		
		model.addAttribute("deviceList", deviceList);
		
		return "admin/adminRegist/adminDevice";
	}
	
	
	@GetMapping("admin/selectDeviceInfo/{deviceNo}")
	@ResponseBody
	public Device selectDeviceInfo(
			@PathVariable("deviceNo") int deviceNo) {
		
		
		return sellingService.selectDetailDevice(deviceNo);
	}
	
	
	
	@PostMapping("registrationChange")
	public String insertBuyingDevice(
			@RequestParam("deviceNo") String deviceNo,
			@RequestParam("gradeSelect") String gradeNumber,
			@RequestParam("colorSelect") String colorNo,
			@RequestParam("capacitySelect") String capacityNumber,
			RedirectAttributes ra
			) {
		
		log.debug("deviceNo : {} ", deviceNo);
		log.debug("gradeNumber : {} ", gradeNumber);
		log.debug("colorNo : {} ", colorNo);
		log.debug("capacityNumber : {} ", capacityNumber);
		
		BuyingDevice newBuyingDevice = new BuyingDevice();
		
		newBuyingDevice.setCapacityNumber(Integer.parseInt(capacityNumber));
		newBuyingDevice.setGradeNumber(gradeNumber);
		newBuyingDevice.setDeviceNo(Integer.parseInt(deviceNo));
		newBuyingDevice.setColorNo(colorNo);
		
		
		int result = service.insertBuyingDevice(newBuyingDevice);
		
		
		if (result > 0) {
			ra.addFlashAttribute("message", "매물이 등록되었습니다.");
		}
		
		return "redirect:/admin/adminRegistration";
		
	}
	
	
	@GetMapping("adminSalePageNation")
	public String salePageNation(
			@RequestParam("cp") int cp,
			@RequestParam("deviceNo") int deviceNo,
			@RequestParam("search") String search,
			Model model
			) {
		//전체 조회 총 개수
		int result = service.result(deviceNo, search);
		int cpCount = result / 10;
		int cp1 = result % 10;
		
		System.out.println(result);
		System.out.println(cpCount);
		System.out.println(cp1);
		
		int pn1 = (cp -1) / 5;
		List<String> pnList = new ArrayList<>();
	
		if(cp1 != 0) {
			cpCount++;
		}
		for(int i = 1 + (pn1 * 5); i <= 5 + (pn1 * 5); i++) {
			if(i > cpCount) {
				break;
			}
			pnList.add(""+i);
		}

		
		model.addAttribute("pnList", pnList);
		model.addAttribute("cpCount", cpCount);
		
		System.out.println(pnList);
		System.out.println(cpCount);
		System.out.println(pnList);
		System.out.println(cpCount);
		
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
	
	
	@GetMapping("adminSalePage")
	public String adminSalePage(
			@RequestParam("cp") int cp,
			@RequestParam("deviceNo") int deviceNo,
			@RequestParam("search") String search,
			Model model
			) {
		
		List<Order> saleList = service.saleListSelect(cp,deviceNo,search);
		
		
		
		model.addAttribute("listView", saleList);
		
		return "admin/adminRegist/sale";
	}
	
	
	@GetMapping("adminProductPageNation")
	public String adminProductPageNation(
			@RequestParam("cp") int cp,
			@RequestParam("search") String search,
			Model model
			) {
		
		//전체 개수 조회해오기
		int result = service.resultAll(search);
		
		int cpCount = result / 10;
		int cp1 = result % 10;
		
		System.out.println(result);
		System.out.println(cpCount);
		System.out.println(cp1);
		
		int pn1 = (cp -1) / 5;
		List<String> pnList = new ArrayList<>();
	
		if(cp1 != 0) {
			cpCount++;
		}
		for(int i = 1 + (pn1 * 5); i <= 5 + (pn1 * 5); i++) {
			if(i > cpCount) {
				break;
			}
			pnList.add(""+i);
		}
		
	

		
		model.addAttribute("pnList", pnList);
		model.addAttribute("cpCount", cpCount);
		
		System.out.println(pnList);
		System.out.println(cpCount);
		System.out.println(pnList);
		System.out.println(cpCount);
		
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
	
	@GetMapping("adminProductPage")
	public String adminProductPage(
			@RequestParam("cp") int cp,
			@RequestParam("search") String search,
			Model model
			) {
		
		log.debug("cp : {}",cp);
		log.debug("search : {}",search);
		log.debug("cp : {}",cp);
		log.debug("search : {}",search);
		
		//rowNum
		List<Device> list = service.list(cp,search);
		
		log.debug("List : {}", list);
		log.debug("List : {}", list);
		
		model.addAttribute("deviceList", list);
		
		System.out.println(list.size());
		System.out.println(list.size());
		System.out.println(list.size());
		System.out.println(list.size());
		
		return "admin/productList";
	}
	
	
	
	
	
	// 공지 관리 화면
	@GetMapping("adminNotice")
	public String adminNoticeView (
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model) {
		
		Map<String, Object> map = service.selectNoticeList(cp);
		
		List<HelpDto> noticeList = (List<HelpDto>)map.get("noticeList");
		Pagination noticePagination = (Pagination)map.get("pagination");
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("noticePagination", noticePagination);
		
		return "admin/adminNotice";
	}
	
	
	
	
	
	// 공지 입력 화면 이동
	@GetMapping("adminNoticeWriteView")
	public String adminNoticeWriteView() {
		
		return "admin/adminNoticeWriteView";
	}
	
	
	// 공지 등록
	@PostMapping("noticeInsert")
	public String adminNoticeInsert(
			@ModelAttribute HelpDto notice,
			RedirectAttributes ra
			) {
		
		int result = service.adminNoticeInsert(notice);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "공지가 등록되었습니다");
		}
		
		return "redirect:/admin/adminNotice";
	}
	
	
	
	@PostMapping("adminNoticeUpdateView")
	public String adminNoticeUpdateView(
			@RequestParam("noticeNumber") int noticeNumber,
			Model model
			) {
		
		HelpDto notice = service.adminNoticeUpdateView(noticeNumber);
		
		model.addAttribute("notice", notice);

		return "admin/adminNoticeUpdateView";
	}
	
	
	@PostMapping("noticeUpdate")
	public String adminNoticeUpdate (
			@ModelAttribute HelpDto notice,
			RedirectAttributes ra){
		
		int result = service.adminNoticeUpdate(notice);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "공지가 수정되었습니다");
		}
		
		return "redirect:/admin/adminNotice";
	}
	
	
	
	@PostMapping("adminNoticeDelete")
	public String adminNoticeDelete (
			@RequestParam("noticeNumber") int noticeNumber,
			RedirectAttributes ra
			) {
		
		int result = service.adminNoticeDelete(noticeNumber);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "공지가 삭제되었습니다");
		}
		
	
	
		
		return "redirect:/admin/adminNotice";
		
	}
	
}
