package edu.kh.bcs.deviceList.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.bcs.deviceList.dto.Filter;
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
	
	

	/* 비동기 필터 기능 */
	@GetMapping("selectFilterList")
	@ResponseBody
	public List<String> selectFilterList(@RequestParam("filterType") String filterType) {
	    return service.selectFilterListByType(filterType);
	}
	
	
	
	/* 검색 결과 반환 */
	@PostMapping("search")
	@ResponseBody
	public List<Filter> searchDevices(@RequestBody List<String> filters) {
		log.info("선택된 필터: {}", filters);
		return service.searchDevices(filters);
	}
	
}
