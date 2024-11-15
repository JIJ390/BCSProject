package edu.kh.bcs.deviceList.service;

import org.springframework.stereotype.Service;

import edu.kh.bcs.deviceList.mapper.DeviceListMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceListServiceImpl implements DeviceListService {

	private final DeviceListMapper mapper;
	
	
}
