package edu.kh.bcs.help.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HelpDto {
	
	private int noticeNumber;
	private String noticeTitle;
	private String noticeContent;
	private String noticeWriteDate;
	private String max;
	

}
