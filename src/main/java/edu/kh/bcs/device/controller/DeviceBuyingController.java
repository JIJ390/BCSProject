package edu.kh.bcs.device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		Device device = service.selectDetailDevice(deviceNo);
		
		log.debug("device : {}", device);
		log.debug("device : {}", device);
		log.debug("device : {}", device);
		log.debug("device : {}", device);
		
		
		model.addAttribute("device", device);
		
		return "deviceBuying/deviceBuying";
		
							
	}
}
