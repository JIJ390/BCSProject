package edu.kh.bcs.device.service;

import org.springframework.stereotype.Service;

import edu.kh.bcs.device.mapper.DeviceSellingMapper;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.SellingDevice;
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
	
	
	
	// 판매 신청
	@Override
	public int acceptSellingDevice(SellingDevice sellingDevice) {
		
		int result = mapper.acceptSellingDevice(sellingDevice);
		
		// 삽입 실패 시
		if (result == 0) return 0;
		
		return sellingDevice.getSellingDeviceNo();
	}
	
	
	// 판매 완료 페이지 정보 가져오기
	@Override
	public SellingDevice selectSellingDevice(int sellingDeviceNo) {
		return mapper.selectSellingDevice(sellingDeviceNo);
	}
}
