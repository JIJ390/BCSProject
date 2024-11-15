package edu.kh.bcs.device.service;

import edu.kh.bcs.device.dto.Device;

public interface DeviceBuyingService {

	/**
	 * 기종 상세 정보 가져 오기
	 * @param deviceNo
	 * @return
	 */
	Device selectDetailDevice(int deviceNo);

}
