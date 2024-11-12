package edu.kh.bcs.point.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.bcs.point.mapper.PointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class PointServiceImpl implements PointService{

	private final PointMapper mapper;
	
	@Override
	public int pointCharge(int amount, int memberNo) {
		
		// 회원 정보 포인트 충전
		int result1 = mapper.pointCharge(amount, memberNo);

		
		// 포인트 충전 내역 업데이트
		int result2 = mapper.insertPointLog(amount, memberNo);
		
		return mapper.selectMemberPoint(memberNo);
	}
}
