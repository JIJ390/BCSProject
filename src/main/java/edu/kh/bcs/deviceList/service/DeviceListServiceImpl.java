package edu.kh.bcs.deviceList.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.deviceList.mapper.DeviceListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceListServiceImpl implements DeviceListService {

	private final DeviceListMapper mapper;
	
	

	// 비동기 필터 조회
	@Override
	public List<String> selectFilterListByType(String filterType) {
		
	    return mapper.selectFilterList(filterType);
	}
	
	
	@Override
	public List<Device> searchDetail(Map<String, Object> obj) {
//		log.debug("aaa : {}", obj);
//		log.debug("aaa : {}", obj);
//		log.debug("aaa : {}", obj);
//		log.debug("aaa : {}", obj);
//		log.debug("aaa : {}", obj);
		
		List<String> hddList = new ArrayList<>();
		
		List<String> ramList = new ArrayList<>();
		
		List<String> inchList = new ArrayList<>();
		
		String brand = null;
		
//		List<Map<String, Object>> hddList = new ArrayList<>();
//		
//		List<Map<String, Object>> ramList = new ArrayList<>();
//		
//		List<Map<String, Object>> inchList = new ArrayList<>();
//		
		
		
		for (Map.Entry<String, Object> entry : obj.entrySet()) {
			
			
			// entry 로는 map 관련 메서드 사용 불가
//		    Map<String, Object> tempMap = new HashMap<>();
		    
//		    tempMap.put(entry.getKey(), entry.getValue());
		    
		    
		    if (entry.getKey().contains("ram")) {
		    	ramList.add((String)entry.getValue());
		    }
		    
		    else if (entry.getKey().contains("hdd")) {
		    	hddList.add((String)entry.getValue());
		    }
		    
		    else if (entry.getKey().contains("inch")) {
		    	inchList.add((String)entry.getValue());
		    }
		    else {
		    	brand = (String)entry.getValue();
		    }

		}
		
		
		log.debug("brand : {}", brand);
		log.debug("brand : {}", brand);
		log.debug("brand : {}", brand);
		log.debug("brand : {}", brand);
		
//		log.debug("ramList : {}", ramList);
//		log.debug("hddList : {}", hddList);
//		log.debug("inchList : {}", inchList);
		
		List<Device> deviceList = mapper.selectDeviceList(ramList, hddList, inchList, brand);
		
//		log.debug("deviceList : {}", deviceList);
//		log.debug("deviceList : {}", deviceList);
//		log.debug("deviceList : {}", deviceList);

		
		
		return deviceList;
	}
	
	
	
	@Override
	public List<Device> phoneList(String category) {
		
//		log.debug("category : {}", category);
		return mapper.phoneList(category);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<Device> brandList(String brand) {
		
		log.debug("브랜드 명 : {}", brand);
		return mapper.brandList(brand);
	}

}
