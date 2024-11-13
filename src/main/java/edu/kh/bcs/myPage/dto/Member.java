package edu.kh.bcs.myPage.dto;

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
public class Member {

	private int memberNo;
	private String memberName;
	private String memberEmail;
	private String memberPw;
	private String memberDelFl;
	private String memberDate;
	private String memberAddress;
	private int memberFl;
	private int memberPoint;
	private String memberTel;
	private String memberId;
	
	
	
}
