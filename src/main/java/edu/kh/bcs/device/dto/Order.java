package edu.kh.bcs.device.dto;

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
public class Order {
	
	private int orderNo;
	private String orderName;
	private String orderTel;
	private String orderComment;
	private String orderAddress;
	private int memberNo;
	private int orderStatusCode;
	private int buyingDeviceNo;
	private String orderDate;
	private int pointLogNo;
	
	// 추가 필드
	private String deviceName;
	private String colorName;
	private String capacityType;
	private String gradeType;
	private String memberId;
	private String orderStatusContent;
	

}
