package edu.kh.bcs.deviceList.controller;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.deviceList.service.DeviceListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DeviceListController {

	private final DeviceListService service;
	
	
	/* 상세 조회 페이지 */
	@GetMapping("deviceList")
	public String deviceLsit() {
		
		return "deviceList/deviceList";
	}
	
	/* 판매 페이지 */
	@GetMapping("deviceSellList")
	public String deviceSellList() {
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
		
//		log.debug("브랜드 값 : {}", brand);
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
	
	
	
	@GetMapping("searchDevice")
	public String searchDevice(@RequestParam("query") String query, Model model) {
		
		String trimmedQuery = query.trim();
		
        log.debug("trimmedQuery {} : ", trimmedQuery);
		
        List<Device> searchResults = service.searchDevices(trimmedQuery);

        // 검색어와 결과를 모델에 추가
        model.addAttribute("query", query); // 검색어
        model.addAttribute("searchResults", searchResults); // 검색 결과
        
        log.debug("searchResults {} : ", searchResults);

        // deviceList.html 페이지 반환
        return "deviceList/deviceList";
    }
	
	
}
	
