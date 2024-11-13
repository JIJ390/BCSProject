package edu.kh.bcs.device.service;

import edu.kh.bcs.deviceDto.Device;

public interface DeviceSellingService {

	/**
	 * 기종 상세 정보 가져오기
	 * @param deviceNo
	 * @return
	 */
	Device selectDetailDevice(int deviceNo);

	/**
	 * 판매 예상 가격 구하기
	 * @param deviceNo
	 * @param plusPrice
	 * @return
	 */
	int expectedPrice(int deviceNo, int plusPrice);

}
