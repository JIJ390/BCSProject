package edu.kh.bcs.device.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.service.DeviceBuyingService;
import edu.kh.bcs.review.dto.Review;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("device/buy")
@RequiredArgsConstructor
public class DeviceBuyingController {
	
	private final DeviceBuyingService service;

	
	/**
	 * 기종 정보 가져오기
	 * @param deviceNo
	 * @param model
	 * @return
	 */
	@GetMapping("{deviceNo}")
	public String deviceBuyView(
			@PathVariable("deviceNo") int deviceNo,
			Model model,
			HttpServletRequest req, 
			HttpServletResponse resp
			) {
		
		// 기종 상세 정보
		Device device = service.selectDetailDevice(deviceNo);
		
		// 시세표 조회
		List<Map<String, String>> priceList = service.selectPriceList(deviceNo);
		
		// 전체 
		Map<String, String> priceStatus = service.priceStatus(deviceNo);
		
		// 리뷰 정보 가져오기
		Map<String, Object> reviewStatus = service.selectReviewStatus(deviceNo);
		
		
		
		
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
		
    // 중복 제거
    deviceNoList.remove(String.valueOf(deviceNo));
    // 0번 index 삽입
    deviceNoList.add(0, String.valueOf(deviceNo));
    
    
    // 최대 크기 5 초과 시 마지막 상품 제거
    if (deviceNoList.size() > 5) {
    	deviceNoList.remove(deviceNoList.size() - 1);
    }
		
    // 최근 본 상품 목록(번호, 이미지, 이름)
    List<Map<String, String>> recentDeviceList = new ArrayList<Map<String, String>>();
		
    
    // 쿠키 기반으로 recentDeviceList 에 값 삽입
		for(String recentDeviceNo : deviceNoList) {
			
			// 쿠키 번호로 기종 정보 가져오기
			Device recentDevice = service.selectRecentDevice(Integer.parseInt(recentDeviceNo));
			
			// 불러온 기종 정보 세팅
			Map<String, String> recentDeviceMap = new HashMap<String, String>();
			
			recentDeviceMap.put("deviceNo", String.valueOf(recentDevice.getDeviceNo()));
			recentDeviceMap.put("deviceImg", recentDevice.getDeviceImg());
			recentDeviceMap.put("deviceName", recentDevice.getDeviceName());
			
			recentDeviceList.add(recentDeviceMap);
		}
		

		
		
		try {
			// cookie 에 , 포함 될 경우 에러 해결 위해 인코딩
			// 쿠키 제거  문자열 배열을 이어붙이기
			String cookieValue = URLEncoder.encode(String.join(",", deviceNoList), StandardCharsets.UTF_8.name());
			
			Cookie cookie = new Cookie("deviceNoList", cookieValue);
			cookie.setMaxAge(60 * 60 * 24 * 7);
			cookie.setPath("/");
			resp.addCookie(cookie);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("device", device);
		model.addAttribute("priceList", priceList);
		model.addAttribute("priceStatus", priceStatus);
		model.addAttribute("reviewList", reviewStatus.get("reviewList"));
		model.addAttribute("reviewCount", reviewStatus.get("reviewCount"));
		model.addAttribute("avgScore", reviewStatus.get("avgScore"));
		model.addAttribute("recentDeviceList", recentDeviceList);
		
		return "deviceBuying/deviceBuying";
		
							
	}
	
	
	
	
	
	
	/**
	 * 내폰 사기 예상 가격
	 * @param map
	 * @return
	 */
	@PostMapping("expectedPrice")
	@ResponseBody
	public int expectedPrice(
			@RequestBody Map<String, Integer> map) {
		
		int plusPrice = map.get("plusPrice");
		int deviceNo = map.get("deviceNo");
		
		return service.expectedPrice(deviceNo, plusPrice);
	}
	
	/**
	 * (다중)내 폰 사기 색상 매물 조회해서 재고 없을 시 불투명
	 * @param map
	 * @return
	 */
	@PostMapping("checkColor")
	@ResponseBody
	public List<Map<String, String>> checkColor(
			@RequestBody int deviceNo) {
		
		
		List<Map<String, String>> list = service.checkColor(deviceNo);
		
		
		log.debug("list : {}", list);
		log.debug("list : {}", list);
		log.debug("list : {}", list);
		log.debug("list : {}", list);
		
		for(Map<String, String> map : list) {
			map.replace("colorNo", String.valueOf(map.get("colorNo")));
			map.replace("colorCount", String.valueOf(map.get("colorCount")));
		}
		
		
		// 파싱 에러 BigDemical
		return list;
	}
	
	
	
	/**
	 * (단일)내 폰 사기 색상 매물 조회해서 재고 없을 시 알림
	 * @param map
	 * @return
	 */
	@PostMapping("selectColor")
	@ResponseBody
	public int selectColor(
			@RequestBody Map<String, Integer> map) {
		
		int colorNo = map.get("colorNo");
		int deviceNo = map.get("deviceNo");
		
		return service.selectColor(colorNo, deviceNo);
	}
	
	
	/**
	 * (다중)내 폰 사기 용량 매물 조회해서 재고 없을 시 불투명
	 * @param map
	 * @return
	 */
	@PostMapping("checkCapacity")
	@ResponseBody
	public List<Map<String, String>> checkCapacity(
			@RequestBody Map<String, Integer> map1) {
		
		int colorNo = map1.get("colorNo");
		int deviceNo = map1.get("deviceNo");
		
		List<Map<String, String>> list = service.checkCapacity(deviceNo, colorNo);
		
		for(Map<String, String> map2 : list) {
			map2.replace("capacityNumber", String.valueOf(map2.get("capacityNumber")));
			map2.replace("capacityCount", String.valueOf(map2.get("capacityCount")));
		}
		
		// 파싱 에러 BigDemical
		return list;
	}
	
	
	/**
	 * (단일)내 폰 사기 용량 매물 조회해서 재고 없을 시 알림
	 * @param map
	 * @return
	 */
	@PostMapping("selectCapacity")
	@ResponseBody
	public int selectCapacity(
			@RequestBody Map<String, Integer> map) {
		
		int colorNo = map.get("colorNo");
		int capacityNumber = map.get("capacityNumber");
		int deviceNo = map.get("deviceNo");
		
		return service.selectCapacity(colorNo, capacityNumber, deviceNo);
	}
	
	
	
	
	/**
	 * (단일)내 폰 사기 용량 매물 조회해서 재고 없을 시 알림
	 * @param map
	 * @return
	 */
	@PostMapping("checkGrade")
	@ResponseBody
	public List<Map<String, String>> checkGrade(
			@RequestBody Map<String, Integer> map1) {
		
		int colorNo = map1.get("colorNo");
		int capacityNumber = map1.get("capacityNumber");
		int deviceNo = map1.get("deviceNo");
		
		List<Map<String, String>> list = service.checkGrade(colorNo, capacityNumber, deviceNo);
		
		for(Map<String, String> map2 : list) {
			map2.replace("gradeNumber", String.valueOf(map2.get("gradeNumber")));
			map2.replace("gradeCount", String.valueOf(map2.get("gradeCount")));
		}
		
		// 파싱 에러 BigDemical
		return list;
	}
	
	
	
	/**
	 * 단일 등급 매물 조회
	 * @param map
	 * @return
	 */
	@PostMapping("selectGrade")
	@ResponseBody
	public int selectGrade(
			@RequestBody Map<String, Integer> map) {
		
		// 매개 변수가 많아서(4 개) 묶어서 처리
		return service.selectGrade(map);
	}
	
	
	
	@PostMapping("reviewPlus")
	public String reviewPlus(
			@RequestBody Map<String, Integer> obj,
			Model model) {
		
		log.debug("obj : {}", obj);
		log.debug("obj : {}", obj);
		log.debug("obj : {}", obj);
		
		int deviceNo = obj.get("deviceNo");
		int reviewCount = obj.get("reviewCount");
		
		Review review = service.reviewPlus(deviceNo, reviewCount);
		
		model.addAttribute("review", review);
		
		return "deviceBuying/reviewPlus";
	}
	
	
	
	
	
	
	
	
}
