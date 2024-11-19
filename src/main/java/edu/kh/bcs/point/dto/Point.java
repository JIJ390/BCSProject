package edu.kh.bcs.point.dto;

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
public class Point {
	
	private int amount;		// 변동 액수
	private int balance;	// 잔액
	
	// 보충 필드
	private int pointLogNo;
	private int memberNo;
	private int originalPoint;
}
