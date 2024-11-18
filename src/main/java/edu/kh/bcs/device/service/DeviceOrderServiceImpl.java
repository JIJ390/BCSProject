package edu.kh.bcs.device.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.mapper.DeviceOrderMapper;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class DeviceOrderServiceImpl implements DeviceOrderService {
	
	private final DeviceOrderMapper mapper;

	
	@Override
		public BuyingDevice orderDeviceView(BuyingDevice buyingDevice) {
			return mapper.orderDeviceView(buyingDevice);
		}
}
