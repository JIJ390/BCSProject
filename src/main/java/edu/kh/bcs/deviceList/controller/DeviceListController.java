package edu.kh.bcs.deviceList.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.service.DeviceBuyingService;
import edu.kh.bcs.deviceList.service.DeviceListService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DeviceListController {

	private final DeviceListService service;
	private final DeviceBuyingService buyingService;
	
	
	/* 상세 조회 페이지 */
	@GetMapping("deviceList")
	public String deviceLsit(
			@RequestParam(value="category", required=false) String category,
			Model model,
			HttpServletRequest req, 
			HttpServletResponse resp
			) {
		
		if (category != null) {
			
			List<Device> deviceList = service.phoneList(category);
			
			model.addAttribute("searchResults", deviceList);
		}
		
		
		
		
		
		//		기종 번호 담을 리스트 선언
		List<String> deviceNoList = new ArrayList<String> ();
		
		
		// 모든 쿠키에서 최근 본 기기 번호 리스트 꺼내기
	    Cookie[] cookies = req.getCookies();
	    // 쿠키가 존재할 시 
	    if (cookies != null) {
	    	
	    	// 각 쿠키마다
	        for (Cookie cookie : cookies) {
	        	// 쿠키 번호 목록 이름이 일치 하는 쿠키 값 꺼내서 리스트에 삽입
	            if ("deviceNoList".equals(cookie.getName())) {
	            	
	            	
	            	 	try {
	            	 		
	            	 		// 쿠키값 디코딩
										String decodedValue = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.name());
		                String value = decodedValue;
										
		                deviceNoList = new ArrayList<>(Arrays.asList(value.split(",")));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}

	                
	                // ',' 구분자 배열로 만들기

	            }
	        }
	    }
			
	 
	    
	    
	    // 최대 크기 5 초과 시 마지막 상품 제거
	    if (deviceNoList.size() > 5) {
	    	deviceNoList.remove(deviceNoList.size() - 1);
	    }
			
	    // 최근 본 상품 목록(번호, 이미지, 이름)
	    List<Map<String, String>> recentDeviceList = new ArrayList<Map<String, String>>();
			
	    
	    // 쿠키 기반으로 recentDeviceList 에 값 삽입
		for(String recentDeviceNo : deviceNoList) {
				
			// 쿠키 번호로 기종 정보 가져오기
			Device recentDevice = buyingService.selectRecentDevice(Integer.parseInt(recentDeviceNo));
					
			// 불러온 기종 정보 세팅
			Map<String, String> recentDeviceMap = new HashMap<String, String>();
					
			recentDeviceMap.put("deviceNo", String.valueOf(recentDevice.getDeviceNo()));
			recentDeviceMap.put("deviceImg", recentDevice.getDeviceImg());
			recentDeviceMap.put("deviceName", recentDevice.getDeviceName());
					
			recentDeviceList.add(recentDeviceMap);
		}
			

		
		
		model.addAttribute("recentDeviceList", recentDeviceList);
		
		
		
		
		
		return "deviceList/deviceList";
	}
	
	/* 판매 페이지 */
	@GetMapping("deviceSellList")
	public String deviceSellList(
			@RequestParam(value="category", required=false) String category,
			Model model) {
		
		if (category != null) {
			
			List<Device> deviceList = service.phoneList(category);
			
			model.addAttribute("searchResults", deviceList);
		}
		
		return "deviceList/deviceSellList";
	}
	
	

	/* 비동기 필터 기능 */
	@GetMapping("selectFilterList")
	@ResponseBody
	public List<String> selectFilterList(@RequestParam("filterType") String filterType) {
		
		
	    return service.selectFilterListByType(filterType);
	}
	
	

	/* 구매 목록 페이지 필터 검색 */
	@PostMapping("searchDetail")
	@ResponseBody
	public List<Device> searchDetail(
			@RequestBody Map<String, Object> obj)  {
		
//		log.debug("obj : {}", obj);
//		log.debug("obj : {}", obj);
//		log.debug("obj : {}", obj);
//		log.debug("obj : {}", obj);
//		log.debug("obj : {}", obj);
//		log.debug("obj : {}", obj);
		

		
		return service.searchDetail(obj);
		
	};
	

	/* 드롭다운 브랜드별 필터 */
	@PostMapping("phoneList")
	@ResponseBody
	public List<Device> phoneList(
			@RequestBody String category){
		
		return service.phoneList(category);
	}
	
	
	
	/* 구매 목록 페이지 브랜드 별 검색 기능 */
	@GetMapping("brandList")
	@ResponseBody
	public List<Device> brandList(
			@RequestParam("brand") String brand) {
		
		log.debug("브랜드 값 : {}", brand);
		return service.brandList(brand);
	}
	
	
	/* 드롭다운 판매 브랜드별 필터 */
	@PostMapping("sellList")
	@ResponseBody
	public List<Device> sellList (
			@RequestBody String category){
		
		return service.sellList(category);
		
	}
	
	/* 브랜드별 판매 페이지 */
	@PostMapping("brandSellList")
	@ResponseBody
	public List<Device> brandSellList(
			@RequestParam("brand") String brand){
		
		return service.brandSellList(brand);
	}
	
	
	/* 검색 기능 */
	@GetMapping("searchDevice")
	public String searchDevice(@RequestParam("query") String query, Model model) {
		
		String trimmedQuery = query.trim();
		
//        log.debug("trimmedQuery {} : ", trimmedQuery);
		
        List<Device> searchResults = service.searchDevices(trimmedQuery);

        // 검색어와 결과를 모델에 추가
        model.addAttribute("query", query); // 검색어
        model.addAttribute("searchResults", searchResults); // 검색 결과
        
//        log.debug("searchResults {} : ", searchResults);

        // deviceList.html 페이지 반환
        return "deviceList/deviceList";
    }
	
	
}
	
