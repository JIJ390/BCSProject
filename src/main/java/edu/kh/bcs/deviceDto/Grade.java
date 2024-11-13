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
public class Grade {

	private int gradeNumber;
	private int deviceNo;
	private String gradeType;
	private int gradeScale;
}
