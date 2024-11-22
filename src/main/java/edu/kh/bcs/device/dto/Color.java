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
public class Color {
	
	private int deviceNo;
	private int colorNo;
	private String colorName;
	private String colorCode;
	private String colorDeviceImg;

	//색상 총 조회
	private int colorCount;
	
}
