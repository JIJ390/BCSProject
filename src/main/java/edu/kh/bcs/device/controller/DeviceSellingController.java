package edu.kh.bcs.device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.bcs.device.service.DeviceSellingService;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.SellingDevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("device/sell")
@RequiredArgsConstructor
public class DeviceSellingController {

	private final DeviceSellingService service;
	
	
	@GetMapping("")
	public String deviceSellView(
			//@RequestParam("deviceNo") int deviceNo
			Model model
			) {
		
		int deviceNo = 3;
		
		Device device = service.selectDetailDevice(deviceNo);
		
		log.info("device : {}", device);
		
		model.addAttribute("device", device);
		
		return "deviceSelling/deviceSelling";
		
	}
	
	
	@PostMapping("expectedPrice")
	@ResponseBody
	public int expectedPrice(
			@RequestBody int plusPrice) {
		
		int deviceNo = 3;
		
		return service.expectedPrice(deviceNo, plusPrice);
	}
	
	
	
	/**
	 * 내폰 팔기 신청 
	 * @param sellingDevice
	 * @return
	 */
	@PostMapping("accept/{deviceNo}")
	public String acceptSellingDevice(
			@ModelAttribute SellingDevice sellingDevice,
			@PathVariable("deviceNo") int deviceNo
			) {
		
		// 세션 로그인으로 가져올 예정
		int memberNo = 13;
		
		sellingDevice.setDeviceNo(deviceNo);
		sellingDevice.setMemberNo(memberNo);
		
		log.debug("sellingDevice : {}", sellingDevice);
		log.debug("sellingDevice : {}", sellingDevice);
		log.debug("sellingDevice : {}", sellingDevice);
		log.debug("sellingDevice : {}", sellingDevice);
		log.debug("sellingDevice : {}", sellingDevice);
		
		int selligDeviceNo = service.acceptSellingDevice(sellingDevice);
		
		log.debug("selligDeviceNo : {}", selligDeviceNo);
		log.debug("selligDeviceNo : {}", selligDeviceNo);
		log.debug("selligDeviceNo : {}", selligDeviceNo);
		
		return "redirect:/";
	}
}
