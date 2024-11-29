package edu.kh.bcs.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.deviceList.service.DeviceListService;
import edu.kh.bcs.help.dto.MainBannerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

	public final DeviceListService service;
	
	@RequestMapping("/")
	public String main(
			Model model) {
		
		List<MainBannerDto> bannerList = service.bannerList();
		
//		log.debug("bannerList {} :", bannerList);
		
		model.addAttribute("bannerList",bannerList);
		
		
		
		
		List<Device> newDevice = service.newDevice();
		
		log.debug("newDevice {} :", newDevice);
		// 모델에 데이터 추가
        model.addAttribute("newDevice", newDevice);
		
		return "main/main";
	}

}
