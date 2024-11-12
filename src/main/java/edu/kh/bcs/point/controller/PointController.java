package edu.kh.bcs.point.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("point")
public class PointController {
	
	
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
			@RequestParam("amount") int amount) {
		
		
		return "pointCharge/pointChargeCompl";
	}
}
