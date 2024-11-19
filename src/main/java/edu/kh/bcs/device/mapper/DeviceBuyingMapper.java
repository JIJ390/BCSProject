package edu.kh.bcs.device.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.bcs.device.dto.Device;

@Mapper
public interface DeviceBuyingMapper {

	/**
	 * 기종	상세 정보 가져오기
	 * @param deviceNo
	 * @return
	 */
	Device selectDetailDevice(int deviceNo);

	
	/**
	 * 예상 가격 가져오기
	 * @param deviceNo
	 * @return
	 */
	int expectedPrice(int deviceNo);

	

	/**
	 * 내 폰 사기 색상 매물 조회해서 재고 없을 시 불투명
	 * @param colorNo
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
	int selectColor(
			@Param("colorNo") int colorNo, 
			@Param("deviceNo") int deviceNo);


	/**
	 * 내 폰 사기 용량 매물 조회해서 재고 없을 시 불투명
	 * @param deviceNo
	 * @param colorNo
	 * @return
	 */
	List<Map<String, String>> checkCapacity(
			@Param("deviceNo") int deviceNo,
			@Param("colorNo") int colorNo);


	
	/**
	 * 내 폰 사기 용량 매물 조회해서 재고 없을 시 알림
	 * @param colorNo
	 * @param capacityNumber
	 * @param deviceNo
	 * @return
	 */
	int selectCapacity(
			@Param("colorNo") int colorNo,
			@Param("capacityNumber") int capacityNumber,
			@Param("deviceNo") int deviceNo);


	/**
	 * 내폰 사기 등급 매물 조회해서 재고 없을 시 불투명
	 * @param colorNo
	 * @param capacityNumber
	 * @param deviceNo
	 * @return
	 */
	List<Map<String, String>> checkGrade(			
			@Param("colorNo") int colorNo,
			@Param("capacityNumber") int capacityNumber,
			@Param("deviceNo") int deviceNo);


	/**
	 * 단일 등급 매물 조회
	 * @param map
	 * @return
	 */
	int selectGrade(Map<String, Integer> map);
	
	
	

}
