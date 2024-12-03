package edu.kh.bcs.point.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.point.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("point")
@RequiredArgsConstructor
public class PointController {
	
	private final PointService service;
	
	
	/**
	 * 충전 페이지 이동
	 * @return
	 */
	@GetMapping("")
	public String PointChargeView (
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			RedirectAttributes ra
			) {
		
		// 로그인 하지 않았을 시
		if (loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			return "redirect:/myPage/myPageLogin";
		}
		
		return "pointCharge/pointCharge";
	}
	
	
	/**
	 * 결제 완료 시 포인트 충전
	 * @param amount
	 * @return
	 */
	@PostMapping("charge")
	@ResponseBody
	public int PointCharge (
			@RequestBody int amount,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		int memberNo = loginMember.getMemberNo();
		
		int beforeChangePoints = loginMember.getMemberPoint();	// 로그인 세션에 담긴 값 가져오기
		
		int result = service.pointCharge(amount, memberNo);
		
		// 포인트 충전 전 포인트 반환
		return beforeChangePoints;
	}
	
	
	@PostMapping("charge/compl")
	public String PointChargeCompl (
			@RequestParam("amount") int amount,
			@RequestParam("beforeChangePoints") int beforeChangePoints,
			@SessionAttribute("loginMember") Member loginMember,
			Model model) {	
		
		int memberNo = loginMember.getMemberNo();

		int currentPoint = service.selectMemberPoint(memberNo);
		
		// 세션 동기화 과정 필요
		loginMember.setMemberPoint(currentPoint);
		
		model.addAttribute("beforeChangePoints", beforeChangePoints);
		model.addAttribute("currentPoint", currentPoint);
		model.addAttribute("amount", amount);
		
		return "pointCharge/pointChargeCompl";
	}
}
