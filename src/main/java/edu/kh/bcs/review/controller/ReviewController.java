package edu.kh.bcs.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.service.DeviceOrderService;
import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.review.dto.Review;
import edu.kh.bcs.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("review")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class ReviewController {

	// 주문 상품 정보 조회 위한 서비스 호출
	private final DeviceOrderService orderService;
	
	private final ReviewService reviewService;
	
	
	@GetMapping("write/{orderNo}")
	public String ReviewWriteView(
			@PathVariable("orderNo") int orderNo,
			RedirectAttributes ra,
			@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		
		
		// 로그인 세션에서 가져오기
//		int memberNo = 1;
		
		Order orderDevice = orderService.selectOrder(orderNo);
		
		if (orderDevice.getMemberNo() != loginMember.getMemberNo()) {
			ra.addFlashAttribute("message", "잘못된 접근입니다");
			return "redirect:/";
		}
		
		orderDevice.setOrderNo(orderNo);
		
		model.addAttribute("orderDevice", orderDevice);
		
		return "review/reviewWrite";
	}
	
	
	
	@PostMapping("insert/{orderNo}")
	public String ReviewInsert(
			@ModelAttribute Review review,
			@RequestParam("imgInput") MultipartFile imgInput,
			RedirectAttributes ra,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		int memberNo = loginMember.getMemberNo();
		
		// result == currentPoint
		int result = reviewService.reviewInsert(review, imgInput, memberNo);
		
		if (result == 0) {
			ra.addFlashAttribute("message", "등록 실패");
		}
		
		loginMember.setMemberPoint(result);
		
		return "redirect:/";
	}
	
	
	@GetMapping("change/{reviewNo}")
	public String ReviewUpdateView(
			@PathVariable("reviewNo") int reviewNo,
			Model model,
			RedirectAttributes ra
			) {
		
		Review review = reviewService.selectReview(reviewNo);
		Order orderDevice = orderService.selectOrder(review.getOrderNo());
		
		
		model.addAttribute("review", review);
		model.addAttribute("orderDevice", orderDevice);
		
		return "review/reviewUpdate";
		
	}
	
	
	
	@PostMapping("update/{reviewNo}")
	public String ReviewUpdate(
			@ModelAttribute Review review,
			@RequestParam("imgInput") MultipartFile imgInput,
			RedirectAttributes ra
			) {
		
		int result = reviewService.reviewUpdate(review, imgInput);
		
		if (result == 0) {
			ra.addFlashAttribute("message", "수정 실패");
		}
		
		return "redirect:/";
	}
}
