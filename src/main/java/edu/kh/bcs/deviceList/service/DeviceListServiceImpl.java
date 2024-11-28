package edu.kh.bcs.deviceList.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.deviceList.mapper.DeviceListMapper;
import edu.kh.bcs.help.dto.MainBannerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.net.aso.c;

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
	
	// 검색
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
		
		
//		log.debug("brand : {}", brand);
//		log.debug("brand : {}", brand);
//		log.debug("brand : {}", brand);
//		log.debug("brand : {}", brand);
		
//		log.debug("ramList : {}", ramList);
//		log.debug("hddList : {}", hddList);
//		log.debug("inchList : {}", inchList);
		
		List<Device> deviceList = mapper.selectDeviceList(ramList, hddList, inchList, brand);
		
//		log.debug("deviceList : {}", deviceList);
//		log.debug("deviceList : {}", deviceList);
//		log.debug("deviceList : {}", deviceList);

		
		
		return deviceList;
	}
	
	
	/* 드롭다운 브랜드별 필터 */
	@Override
	public List<Device> phoneList(String category) {
		
//		log.debug("category : {}", category);
		return mapper.phoneList(category);
	}
	

	/* 드롭다운 판매 브랜드별 필터 */
	@Override
	public List<Device> sellList(String category) {
		return mapper.sellList(category);
	}
	
	
	
	@Override
	public List<Device> brandSellList(String brand) {
		
//		log.debug("브랜드 명 : {}", brand);
		return mapper.brandSellList(brand);
	}
	
	
	
	
	
	
	
	
	@Override
	public List<Device> brandList(String brand) {
		
//		log.debug("브랜드 명 : {}", brand);
		return mapper.brandList(brand);
	}
	
	
	
	@Override
	public List<Device> searchDevices(String query) {
		
		// 한글 체크: 유니코드 범위 확인
        boolean isKorean = 
        		query.chars().allMatch(c -> 
        		(c >= '\uAC00' && c <= '\uD7A3') || // 한글
                (c >= '0' && c <= '9') ||			// 숫자 포함
                (c == '\u0020'));  					// 띄어쓰기 포함
                
        
        // 한글일 경우 한글로 검색해서 반환
        if (isKorean) {
        	return mapper.searchDevicesKor(query);
        }
		
        // 한글이 포함되지 않은 경우
		log.debug("검색 : {}", query);
		return mapper.searchDevices(query);
	}
	
	
	
	
	
	
	@Override
	public List<MainBannerDto> bannerList() {
		
		List<MainBannerDto> bannerList =  mapper.bannerList();
		
		for (MainBannerDto banner : bannerList) {
			
			// 왼쪽일 경우에
			if (banner.getMainBannerLr().equals("left")) {
				banner.setMainBannerCheck(true);
			} else {
				banner.setMainBannerCheck(false);
			} 
			
			if (banner.getMainBannerFontColor().equals("black")) {
				banner.setMainBannerColor(true);
			} else {
				banner.setMainBannerColor(false);
			}
					
			
		}

		
		return bannerList;
	}
}
