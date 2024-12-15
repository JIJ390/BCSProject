package edu.kh.bcs.location.dto;

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
public class locationDto {
	
	private int locationNo;
	private String latitudeNo;
	private String longitudeNo;
	private String storeName;
	private String address;
	private int streetNumber;
}
