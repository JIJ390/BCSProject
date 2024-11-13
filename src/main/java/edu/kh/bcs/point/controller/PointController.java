package edu.kh.bcs.point.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public int PointCharge (
			@RequestBody int amount
			) {
		
		int memberNo = 1;
		
		int beforeChangePoints = 0;	// 로그인 세션에 담긴 값 가져오기
		
		int result = service.pointCharge(amount, memberNo);
		
		// 포인트 충전 전 포인트 반환
		return beforeChangePoints;
	}
	
	
	@PostMapping("charge/compl")
	public String PointChargeCompl (
			@RequestParam("amount") int amount,
			@RequestParam("beforeChangePoints") int beforeChangePoints,
			Model model) {	
		
		int memberNo = 1;

		int currentPoint = service.selectMemberPoint(memberNo);
		
		// 세션 동기화 과정 필요
		
		model.addAttribute("beforeChangePoints", beforeChangePoints);
		model.addAttribute("currentPoint", currentPoint);
		model.addAttribute("amount", amount);
		
		return "pointCharge/pointChargeCompl";
	}
}
