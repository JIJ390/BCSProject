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
public class EventDto {
	
	// 이벤트용
	
		private int eventNumber;
		private String eventImage; // 원본 이미지
		private String eventWriteDate;
		private String eventDelFl;
		private String eventTitle;
		private String eventContent;
		private String eventThumbnail; // 썸네일 이미지
		
		private String max;

}
