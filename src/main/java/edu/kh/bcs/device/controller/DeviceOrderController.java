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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.device.service.DeviceOrderService;
import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.point.dto.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("device/order")
@SessionAttributes({"loginMember"})
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
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			Model model,
			RedirectAttributes ra
			) {
		
		
		buyingDevice.setDeviceNo(deviceNo);
		
		BuyingDevice orderBuyingDevice = service.orderDeviceView(buyingDevice);
		
		// 해당 매물이 없을 시
		if (orderBuyingDevice == null) {
			ra.addFlashAttribute("message", "선택된 매물이 품절 상태입니다");
			return "redirect:/device/buy" + deviceNo;
		}
		
		// 로그인 하지 않았을 시
		if (loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			return "redirect:/myPage/myPageLogin";
		}

		
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
			@SessionAttribute("loginMember") Member loginMember,
			@ModelAttribute Order order,
			RedirectAttributes ra
			) {
		
		// 매물 번호 세팅
		order.setBuyingDeviceNo(buyingDeviceNo);
		
		// 로그인 세션에서 가져오도록 수정 필요
		int memberNo = loginMember.getMemberNo();
		
		order.setMemberNo(memberNo);
		
		int orderNo = service.orderDevicePayment(order);
		
		// 세션 동기화
		int currentPoint = service.selectCurrnetPoint(memberNo);
		loginMember.setMemberPoint(currentPoint);
		
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
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			RedirectAttributes ra,
			Model model) {
		
		Order order = service.selectOrder(orderNo);
		
		// 로그인 하지 않았을 시
		// sellingDevice 가 없을 시
		// 다른 회원 정보로 접근 시
		if ((loginMember == null) || (order == null) || (loginMember.getMemberNo() != order.getMemberNo())) {
			ra.addFlashAttribute("message", "잘못된 접근입니다");
			return "redirect:/";
		}
		
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
