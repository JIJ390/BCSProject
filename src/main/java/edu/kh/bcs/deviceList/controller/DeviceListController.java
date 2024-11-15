package edu.kh.bcs.deviceList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
	

}
