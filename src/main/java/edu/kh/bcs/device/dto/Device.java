package edu.kh.bcs.device.dto;

import java.util.List;

import edu.kh.bcs.device.dto.Capacity;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Grade;
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
public class Device {

	private int deviceNo;
	private String deviceReleaseDate;
	private int deviceReleasePrice;
	private String deviceImg;
	private String deviceOs;
	private String deviceResolution;
	private String devicePixel;
	private String deviceFrontPixel;
	private String deviceBackPixel;
	private String deviceWirelessCharge;
	private String deviceFastCharge;
	private String deviceWeight;
	private String deviceBatteryCapacity;
	private String deviceName;
	private int deviceBuyingPrice;
	private int deviceSellingPrice;
	private String deviceBrand;
	private String deviceCpuName;
	
	private String ramCapacity;
	private String displaySize;
	
	private List<Color> colorList;
	private List<Grade> gradeList;
	private List<Capacity> capacityList;
}