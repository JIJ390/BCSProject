package edu.kh.bcs.device.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyingDevice {
	
	// DB 기반 필드
	private int buyingDeviceNo;
	private String buyingDeviceUploadDate;
	private String buyingDeviceSoldDate;
	private int buyingDevicePrice;
	private int capacityNumber;
	private int deviceNo;
	private String gradeNumber;
	private String soldFl;
	private String colorNo;
	
	
	// 보조 필드
	private String capacityType;
	private String gradeType;
	private String colorName;
	private String deviceName;
	
	

}
