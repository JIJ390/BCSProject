package edu.kh.bcs.device.controller;

import java.util.Map;

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
	
	
	@GetMapping("{deviceNo}")
	public String deviceSellView(
			@PathVariable("deviceNo") int deviceNo,
			Model model
			) {
		
		Device device = service.selectDetailDevice(deviceNo);
		
		log.debug("device : {}", device);
		log.debug("device : {}", device);
		log.debug("device : {}", device);
		log.debug("device : {}", device);
		
		model.addAttribute("device", device);
		
		return "deviceSelling/deviceSelling";
		
	}
	
	
	@PostMapping("expectedPrice")
	@ResponseBody
	public int expectedPrice(
			@RequestBody Map<String, Integer> map) {
		
		int plusPrice = map.get("plusPrice");
		int deviceNo = map.get("deviceNo");
		
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
		
		int selligDeviceNo = service.acceptSellingDevice(sellingDevice);
		
		return "redirect:/device/sell/compl/" + selligDeviceNo;
	}
	
	
	@GetMapping("compl/{sellingDeviceNo}")
	public String deviceSellCompl(
			@PathVariable("sellingDeviceNo") int sellingDeviceNo,
			Model model) {
		
		SellingDevice sellingDevice = service.selectSellingDevice(sellingDeviceNo);
		
		// 추후 로그인 정보에 따라 접근 불가 지정해야함
		
		log.info("sellingDevice : {}", sellingDevice);
		log.info("sellingDevice : {}", sellingDevice);
		log.info("sellingDevice : {}", sellingDevice);
		log.info("sellingDevice : {}", sellingDevice);
		
		
		model.addAttribute("sellingDevice", sellingDevice);
		
		return "deviceSelling/deviceSellingCompl";
	}
}
