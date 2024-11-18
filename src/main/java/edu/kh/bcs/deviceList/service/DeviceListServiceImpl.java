package edu.kh.bcs.deviceList.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.deviceList.dto.Filter;
import edu.kh.bcs.deviceList.mapper.DeviceListMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceListServiceImpl implements DeviceListService {

	private final DeviceListMapper mapper;
	
	

	// 비동기 필터 조회
	@Override
	public List<String> selectFilterListByType(String filterType) {
	    return mapper.selectFilterList(filterType);
	}
	
	
	// 비동기로 검색하기
	@Override
	public List<Filter> searchDevices(List<String> filters) {
		return mapper.searchDevices(filters);
	}
}
