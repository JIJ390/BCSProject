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
public class Grade {

	private int gradeNumber;
	private int deviceNo;
	private int gradeType;
	private int gradePrice;
	private int gradeSellPrice;
}
