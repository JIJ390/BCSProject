package edu.kh.bcs.device.service;

import org.springframework.stereotype.Service;

import edu.kh.bcs.device.mapper.DeviceSellingMapper;
import edu.kh.bcs.deviceDto.Device;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceSellingServiceImpl implements DeviceSellingService {

	private final DeviceSellingMapper mapper;

	
	@Override
	public Device selectDetailDevice(int deviceNo) {
		return mapper.selectDetailDevice(deviceNo);
	}

	// 예상 가격 구하기
	@Override
	public int expectedPrice(int deviceNo, int plusPrice) {
		
		int deviceSellingPrice = mapper.expectedPrice(deviceNo);
		
		
		
		return deviceSellingPrice + plusPrice;
	}
}
