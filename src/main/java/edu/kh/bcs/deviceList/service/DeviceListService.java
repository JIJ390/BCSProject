package edu.kh.bcs.deviceList.service;

import java.util.List;

import edu.kh.bcs.deviceList.dto.Filter;

public interface DeviceListService {

	/** 비동기 필터 조회
	 * @param filterType 
	 * @return
	 */
	  List<String> selectFilterListByType(String filterType);

	
}
