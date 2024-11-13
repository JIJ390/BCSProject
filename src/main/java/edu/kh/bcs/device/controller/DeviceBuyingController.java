package edu.kh.bcs.device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("device/buy")
public class DeviceBuyingController {

	@GetMapping("")
	public String deviceBuyView() {
		
		return "deviceBuying/deviceBuying";
		
							
	}
}
