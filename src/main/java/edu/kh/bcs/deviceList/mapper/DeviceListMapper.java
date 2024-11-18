package edu.kh.bcs.deviceList.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.deviceList.dto.Filter;

@Mapper
public interface DeviceListMapper {

	
	/** 비동기 필터 조회
	 * @param filterType 
	 * @return
	 */
	List<String> selectFilterList(String filterType);

	
	/** 비동기로 검색
	 * @param filters
	 * @return
	 */
	List<Filter> searchDevices(List<String> filters);

}
