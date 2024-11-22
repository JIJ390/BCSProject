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


	List<Device> phoneList(String category);


	
	





	

	



	
	

	
	
	
}
