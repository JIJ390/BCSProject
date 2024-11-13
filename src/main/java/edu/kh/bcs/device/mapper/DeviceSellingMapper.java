package edu.kh.bcs.device.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.deviceDto.Device;

@Mapper
public interface DeviceSellingMapper {

	Device selectDetailDevice(int deviceNo);

	/**
	 * 예상 가격 구하기
	 * @param deviceNo
	 * @return
	 */
	int expectedPrice(int deviceNo);



}
