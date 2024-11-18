package edu.kh.bcs.device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.service.DeviceOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("device/order")
@RequiredArgsConstructor
public class DeviceOrderController {
	
	private final DeviceOrderService service;
	
	@PostMapping("{deviceNo}")
	public String orderDeviceView(
			@PathVariable("deviceNo") int deviceNo,
			@ModelAttribute BuyingDevice buyingDevice,
			Model model
			) {
		
		BuyingDevice orderBuyingDevice = service.orderDeviceView(buyingDevice);
		
		log.debug("aaa : {}", orderBuyingDevice);
		log.debug("aaa : {}", orderBuyingDevice);
		log.debug("aaa : {}", orderBuyingDevice);
		log.debug("aaa : {}", orderBuyingDevice);
		log.debug("aaa : {}", orderBuyingDevice);
		log.debug("aaa : {}", orderBuyingDevice);
		
		
		return "deviceBuying/deviceOrder";
		
							
	}

}
