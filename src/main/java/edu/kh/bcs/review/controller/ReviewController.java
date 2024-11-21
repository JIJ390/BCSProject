package edu.kh.bcs.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.service.DeviceOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {

	// 주문 상품 정보 조회 위한 서비스 호출
	private final DeviceOrderService orderService;
	
	
	@GetMapping("write/{orderNo}")
	public String write(
			@PathVariable("orderNo") int orderNo,
			Model model) {
		
		Order orderDevice = orderService.selectOrder(orderNo);
		
		model.addAttribute("orderDevice", orderDevice);
		
		return "review/reviewWrite";
	}
	
}
