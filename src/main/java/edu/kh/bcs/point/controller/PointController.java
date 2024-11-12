package edu.kh.bcs.point.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.bcs.point.dto.Point;
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
	public String PointChargeView () {
		return "pointCharge/pointCharge";
	}
	
	
	/**
	 * 결제 완료 시 포인트 충전
	 * @param amount
	 * @return
	 */
	@PostMapping("charge")
	public String PointCharge (
			Model model) {
		
		
//		@RequestParam("amount") int amount,
		int memberNo = 1;
		
		System.out.println("asdasdasdasasd");		
		System.out.println("asdasdasdasasd");		
		System.out.println("asdasdasdasasd");		
		System.out.println("asdasdasdasasd");		
		System.out.println("asdasdasdasasd");		
		
		int amount = 100;
		
		log.debug("amount : {}", amount);
		log.debug("amount : {}", amount);
		log.debug("amount : {}", amount);
		log.debug("amount : {}", amount);
		log.debug("amount : {}", amount);
		
		int beforeChangePoints = 0;	// 로그인 세션에 담긴 값 가져오기
		
		int memberPoint = service.pointCharge(amount, memberNo);
		
		model.addAttribute("beforeChangePoints", beforeChangePoints);
		model.addAttribute("amount", amount);
		model.addAttribute("currentPoint", memberPoint);
		
		return "pointCharge/pointChargeCompl";
	}
}
