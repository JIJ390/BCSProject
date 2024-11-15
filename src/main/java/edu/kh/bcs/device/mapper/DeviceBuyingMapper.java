package edu.kh.bcs.device.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.device.dto.Device;

@Mapper
public interface DeviceBuyingMapper {

	/**
	 * 기종	상세 정보 가져오기
	 * @param deviceNo
	 * @return
	 */
	Device selectDetailDevice(int deviceNo);

}
