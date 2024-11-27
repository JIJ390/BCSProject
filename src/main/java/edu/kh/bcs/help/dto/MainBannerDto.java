package edu.kh.bcs.help.dto;

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
public class MainBannerDto {
	
	private String mainBannerNo;
	private String mainBannerImg;
	private String mainBannerTitle;
	private String mainBannerContent;
	private String mainBannerLink;
	private String mainBannerFontColor;
	private String mainBannerLr;
	
}
