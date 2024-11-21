package edu.kh.bcs.device.controller;

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

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.service.DeviceBuyingService;
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
			Model model
			) {
		
		// 기종 상세 정보
		Device device = service.selectDetailDevice(deviceNo);
		
		// 시세표 조회
		List<Map<String, String>> priceList = service.selectPriceList(deviceNo);
		
		// 전체 
		Map<String, String> priceStatus = service.priceStatus(deviceNo);
		
		
		log.debug("priceStatus {} ", priceStatus);
		log.debug("priceStatus {} ", priceStatus);
		log.debug("priceStatus {} ", priceStatus);
		log.debug("priceStatus {} ", priceStatus);
		log.debug("priceStatus {} ", priceStatus);
		
		
		model.addAttribute("device", device);
		model.addAttribute("priceList", priceList);
		model.addAttribute("priceStatus", priceStatus);
		
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
}
