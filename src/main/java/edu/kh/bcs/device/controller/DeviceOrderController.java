package edu.kh.bcs.device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.bcs.device.dto.Device;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("device/order")
@RequiredArgsConstructor
public class DeviceOrderController {
	
	@GetMapping("")
	public String deviceBuyView(
			) {
		return "deviceBuying/deviceOrder";
		
							
	}

}
