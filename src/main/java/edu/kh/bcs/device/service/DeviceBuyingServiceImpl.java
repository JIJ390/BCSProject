package edu.kh.bcs.device.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.mapper.DeviceBuyingMapper;
import edu.kh.bcs.review.dto.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceBuyingServiceImpl implements DeviceBuyingService {

	private final DeviceBuyingMapper mapper;
	
	// 기종 상세 정보 가져오기
	@Override
	public Device selectDetailDevice(int deviceNo) {
		return mapper.selectDetailDevice(deviceNo);
	}
	
	
	// 내 폰 사기 예상 가격
	@Override
	public int expectedPrice(int deviceNo, int plusPrice) {
		return mapper.expectedPrice(deviceNo) + plusPrice;
	}
	
	
	// 내 폰 사기 색상 매물 조회해서 재고 없을 시 불투명
	@Override
	public List<Map<String, String>> checkColor(int deviceNo) {
		return mapper.checkColor(deviceNo);
	}
	
	
	// 내 폰 사기 색상 매물 조회
	@Override
	public int selectColor(int colorNo, int deviceNo) {
		return mapper.selectColor(colorNo, deviceNo);
	}
	
	
	// 내 폰 사기 용량 매물 조회해서 재고 없을 시 불투명
	@Override
	public List<Map<String, String>> checkCapacity(int deviceNo, int colorNo) {
		return mapper.checkCapacity(deviceNo, colorNo);
	}
	
	
	
	// 내 폰 사기 용량 매물 조회해서 재고 없을 시 알림
	@Override
	public int selectCapacity(int colorNo, int capacityNumber, int deviceNo) {
		return mapper.selectCapacity(colorNo, capacityNumber, deviceNo);
	}
	
	
	// 내폰 사기 등급 매물 조회해서 재고 없을 시 불투명
	@Override
	public List<Map<String, String>> checkGrade(int colorNo, int capacityNumber, int deviceNo) {
		return mapper.checkGrade(colorNo, capacityNumber, deviceNo);
	}
	
	
	// 단일 등급 매물 조회
	@Override
	public int selectGrade(Map<String, Integer> map) {
		return mapper.selectGrade(map);
	}
	
	
	// 시세표
	@Override
	public List<Map<String, String>> selectPriceList(int deviceNo) {
		return mapper.selectPriceList(deviceNo);
	}
	
	
	// 세부 가격 정보
	@Override
	public Map<String, String> priceStatus(int deviceNo) {
		
		return mapper.priceStatus(deviceNo);
	}
	
	
	
	// 리뷰 정보 
	@Override
	public Map<String, Object> selectReviewStatus(int deviceNo) {

		// 리뷰 개수 + 평점
		Map<String, Object> reviewStatus = mapper.selectReviewStatus(deviceNo);
		
		List<Review> reviewList = mapper.selectReviewList(deviceNo);
		
		reviewStatus.put("reviewList", reviewList);
		
		
		return reviewStatus;
	}
	
	
	// 리뷰 추가해서 불러오기
	@Override
	public Review reviewPlus(int deviceNo, int reviewCount) {
		
		// 다음 리뷰가 존재 하는지 확인
		Map<String, String> map = mapper.checkNextReview(deviceNo, reviewCount);
		
		log.debug("map : {}", map);
		
		Review review = mapper.reviewPlus(deviceNo, reviewCount);
		
		log.debug("review : {}", review.getReviewCheck());
		
		// 다음 리뷰가 존재하지 않을때
		if(map == null) {
			review.setReviewCheck(1);
		}
		
		return mapper.reviewPlus(deviceNo, reviewCount);
	}
	

	// 최근 기종
	@Override
	public Device selectRecentDevice(int recentDeviceNo) {
		return mapper.selectRecentDevice(recentDeviceNo);
	}
	
	
}
