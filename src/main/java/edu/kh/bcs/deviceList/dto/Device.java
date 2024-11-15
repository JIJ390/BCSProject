package edu.kh.bcs.deviceList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Device {
	private int devicceNo;
	private String deviceImg;
	private String deviceName;
	private int deviceBuyingPrice;
	private String deviceBrand;
	private int deviceRamCode;
	
}
