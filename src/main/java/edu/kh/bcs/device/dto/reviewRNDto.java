package edu.kh.bcs.device.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class reviewRNDto {

	private int reviewNotiNo; //REVIEW_NOTI_NO
	private String reviewNotiRead; // REVIEW_NOTI_READ
	private int orderNo; // ORDER_NO
	
	private String deviceName;
}
