package edu.kh.bcs.deviceDto;

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
public class Capacity {

	private int deviceNo;
	private int capacityNumber;
	private String capacityType;
	private int capacityPrice;
	private int capacitySellPrice;
}
