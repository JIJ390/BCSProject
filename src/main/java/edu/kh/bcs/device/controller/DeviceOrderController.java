package edu.kh.bcs.device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.device.service.DeviceOrderService;
import edu.kh.bcs.point.dto.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("device/order")
@RequiredArgsConstructor
public class DeviceOrderController {
	
	private final DeviceOrderService service;
	
	
	/**
	 * 구매 기종 정보 + 예상 가격 만들어 결제 페이지 이동
	 * @param deviceNo
	 * @param buyingDevice
	 * @param model
	 * @return
	 */
	@PostMapping("{deviceNo}")
	public String orderDeviceView(
			@PathVariable("deviceNo") int deviceNo,
			@ModelAttribute BuyingDevice buyingDevice,
			Model model
			) {
		
		
		buyingDevice.setDeviceNo(deviceNo);
		
		BuyingDevice orderBuyingDevice = service.orderDeviceView(buyingDevice);

		
		model.addAttribute("orderBuyingDevice", orderBuyingDevice);
		
		return "deviceBuying/deviceOrder";
		
	}
	
	
	
	/**
	 * 주문 정보 받아서 주문 insert + 결제 완료 페이지 이동
	 * @param buyingDeviceNo
	 * @param order
	 * @return
	 */
	@PostMapping("payment/{buyingDeviceNo}")
	public String orderDevicePayment(
			@PathVariable("buyingDeviceNo") int buyingDeviceNo,
			@RequestParam("deviceNo") int deviceNo,
			@ModelAttribute Order order,
			RedirectAttributes ra
			) {
		
		// 매물 번호 세팅
		order.setBuyingDeviceNo(buyingDeviceNo);
		
		// 로그인 세션에서 가져오도록 수정 필요
		int memberNo = 13;
		
		order.setMemberNo(memberNo);
		
		log.debug("order : {}", order);
		log.debug("order : {}", order);
		log.debug("order : {}", order);
		log.debug("order : {}", order);
		
		int orderNo = service.orderDevicePayment(order);
		
		// 잔액 부족 시 구매 페이지 리다이렉트
		if (orderNo < 0) {
			ra.addFlashAttribute("message", "잔액이 부족합니다");
			return "redirect:/device/buy/" + deviceNo;
		}
		
		return "redirect:/device/order/compl/" + orderNo;
		
	}

	
	
	
	@GetMapping("compl/{orderNo}")
	public String deviceSellCompl(
			@PathVariable("orderNo") int orderNo,
			Model model) {
		
		Order order = service.selectOrder(orderNo);
		
		int memberNo = order.getMemberNo();
		
		Point point = service.selectPointLog(orderNo);
		
		log.info("order : {}", order);
		log.info("order : {}", order);
		log.info("order : {}", order);
		log.info("order : {}", order);
		
		String[] arr = order.getOrderAddress().split(",");
		
		// 가게 주소 잘라내기
		model.addAttribute("postcode"     , arr[0]);
		model.addAttribute("address"      , arr[1]);
		model.addAttribute("detailAddress", arr[2]);
		
		model.addAttribute("order", order);
		model.addAttribute("point", point);
		
		return "deviceBuying/deviceOrderCompl";
	}
	
	
	
	
}
