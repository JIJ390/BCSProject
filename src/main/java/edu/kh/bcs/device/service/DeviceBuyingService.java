package edu.kh.bcs.device.service;

import java.util.List;
import java.util.Map;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.review.dto.Review;

public interface DeviceBuyingService {

	/**
	 * 기종 상세 정보 가져 오기
	 * @param deviceNo
	 * @return
	 */
	Device selectDetailDevice(int deviceNo);

	
	/**
	 * 내폰 사기 예상 가격
	 * @param deviceNo
	 * @param plusPrice
	 * @return
	 */
	int expectedPrice(int deviceNo, int plusPrice);

	
	

	/**
	 * 내폰 사기 색상 매물 조회해서 재고 없을 시 불투명
	 * @param deviceNo
	 * @return
	 */
	List<Map<String, String>> checkColor(int deviceNo);
	

	/**
	 * 색상 매물 조회
	 * @param colorNo
	 * @param deviceNo
	 * @return
	 */
	int selectColor(int colorNo, int deviceNo);


	/**
	 * (다중)내폰 사기 용량 매물 조회해서 재고 없을 시 불투명
	 * @param deviceNo
	 * @return
	 */
	List<Map<String, String>> checkCapacity(int deviceNo, int colorNo);

	
	/**
	 * (단일)내 폰 사기 용량 매물 조회해서 재고 없을 시 알림
	 * @param colorNo
	 * @param capacityNumber
	 * @param deviceNo
	 * @return
	 */
	int selectCapacity(int colorNo, int capacityNumber, int deviceNo);


	
	/**
	 * (다중)내폰 사기 등급 매물 조회해서 재고 없을 시 불투명
	 * @param colorNo
	 * @param capacityNumber
	 * @param deviceNo
	 * @return
	 */
	List<Map<String, String>> checkGrade(int colorNo, int capacityNumber, int deviceNo);


	/**
	 * 단일 등급 매물 조회
	 * @param map
	 * @return
	 */
	int selectGrade(Map<String, Integer> map);


	/**
	 * 시세표 조회
	 * @param deviceNo
	 * @return
	 */
	List<Map<String, String>> selectPriceList(int deviceNo);


	/**
	 * 가격 정보
	 * @param deviceNo
	 * @return
	 */
	Map<String, String> priceStatus(int deviceNo);


	/**
	 * 리뷰 정보(리뷰 리스트 + 전체 별점
	 * @param deviceNo
	 * @return
	 */
	Map<String, Object> selectReviewStatus(int deviceNo);


	/**
	 * 리뷰 정보 불러오기
	 * @param deviceNo
	 * @param reviewCount
	 * @return
	 */
	Review reviewPlus(int deviceNo, int reviewCount);


	/**
	 * 최근 본 기종 정보
	 * @param recentDeviceNo
	 * @return
	 */
	Device selectRecentDevice(int recentDeviceNo);







}
