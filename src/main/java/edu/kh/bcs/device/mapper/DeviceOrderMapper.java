package edu.kh.bcs.device.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.device.dto.BuyingDevice;

@Mapper
public interface DeviceOrderMapper {

	BuyingDevice orderDeviceView(BuyingDevice buyingDevice);

}
