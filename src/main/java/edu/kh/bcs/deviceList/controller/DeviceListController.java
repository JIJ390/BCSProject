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
	
	
	
	@PostMapping("search")
	@ResponseBody
	public List<Filter> searchDevices(@RequestBody List<String> filters) {
	    if (filters == null || filters.isEmpty()) {
	        throw new IllegalArgumentException("필터가 제공되지 않았습니다.");
	    }

	    log.info("선택된 필터: {}", filters);

	    // 검색 로직 호출
	    List<Filter> result = service.searchDevices(filters);

	    if (result.isEmpty()) {
	        log.info("검색 결과가 없습니다.");
	    }

	    return result;
	}

}
