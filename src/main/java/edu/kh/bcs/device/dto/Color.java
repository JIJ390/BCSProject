package edu.kh.bcs.device.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Color {
	
	private int deviceNo;
	private int colorNo;
	private String colorName;
	private String colorCode;
	private String colorDeviceImg;

	
	//컬러 총 합 계수
	private int colorResult;
	
	
	//파일 업로드 편의성
	private MultipartFile uploadFile; // 개발 편의성
}
