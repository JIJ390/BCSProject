package edu.kh.bcs.device.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.SellingDevice;

@Mapper
public interface DeviceSellingMapper {

	Device selectDetailDevice(int deviceNo);

	/**
	 * 예상 가격 구하기
	 * @param deviceNo
	 * @return
	 */
	int expectedPrice(int deviceNo);

	
	/**
	 * 판매 신청
	 * @param sellingDevice
	 * @return
	 */
	int acceptSellingDevice(SellingDevice sellingDevice);



}
