package edu.kh.bcs.review.service;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.review.dto.Review;

public interface ReviewService {

	
	/**
	 * 이미지 별점 내용을 담아 리뷰 insert
	 * @param review
	 * @param imgInput
	 * @return
	 */
	int reviewInsert(Review review, MultipartFile imgInput);

	/**
	 *  리뷰 정보 select 
	 * @param reviewNo
	 * @return
	 */
	Review selectReview(int reviewNo);

	/**
	 * 리뷰 수정
	 * @param review
	 * @param imgInput
	 * @return
	 */
	int reviewUpdate(Review review, MultipartFile imgInput);

}
