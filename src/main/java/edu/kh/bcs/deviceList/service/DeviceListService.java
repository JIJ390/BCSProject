package edu.kh.bcs.deviceList.service;

import java.util.List;
import java.util.Map;

import edu.kh.bcs.device.dto.Device;

public interface DeviceListService {

	/** 비동기 필터 조회
	 * @param filterType 
	 * @return
	 */
	List<String> selectFilterListByType(String filterType);

	
	/**
	 * 검색
	 * @param obj : ram, hdd, inch
	 * @return
	 */
	List<Device> searchDetail(Map<String, Object> obj);


	/* 드롭다운 브랜드별 필터 */
	List<Device> phoneList(String category);


	/* 구매 목록 페이지 브랜드 별 검색 기능 */
	List<Device> brandList(String brand);

	
	/* 드롭다운 판매 브랜드별 필터 */
	List<Device> sellList(String category);


	
	





	

	



	
	

	
	
	
}
