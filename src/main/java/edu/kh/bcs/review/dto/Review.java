package edu.kh.bcs.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review {

	private int reviewNo;
	private String reviewImg;
	private String reviewContent;
	private String reviewScore;
	private String reviewDate;
	private int orderNo;
}
