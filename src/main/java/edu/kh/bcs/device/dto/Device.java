package edu.kh.bcs.device.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.device.dto.Capacity;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Grade;
import edu.kh.bcs.myPage.dto.Member;
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
	private String deviceNameKor;
	private int deviceBuyingPrice;
	private int deviceSellingPrice;
	private String deviceBrand;
	private String deviceCpuName;
	
	
	private String deviceDisplayCode;
	
	private int deviceRamCode;
	
	private String ramCapacity;
	private String displaySize;
	
	private String deviceDate;
	private String colorResult;
	
	
	private List<Color> colorList;
	private List<Grade> gradeList;
	private List<Capacity> capacityList;
	
	private List<BuyingDevice> buyingDevice;
	private List<Order> order;
	private List<Member> member;

	
	
	//주문 조회
	private int orderCount;
	//현재 개수
	private int deviceCount;
	
	
	
	

}