package edu.kh.bcs.deviceList.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.deviceList.dto.Filter;

@Mapper
public interface DeviceListMapper {

	
	/** 비동기 필터 조회
	 * @param filterType 
	 * @return
	 */
	List<String> selectFilterList(String filterType);

	
	
	
	String searchDetail(Filter filter);

	
	

	

}
