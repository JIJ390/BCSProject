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
public class Filter {
	
	private String deviceRamCapacity;
	private String capacityType;
	private String deviceDisplaySize;
	
	
}
