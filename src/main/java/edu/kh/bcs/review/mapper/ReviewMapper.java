package edu.kh.bcs.review.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.review.dto.Review;

@Mapper
public interface ReviewMapper {

	
	/**
	 * 리뷰 인서트
	 * @param review
	 * @return
	 */
	int reviewInsert(Review review);

	/**
	 * 리뷰 셀렉트
	 * @param reviewNo
	 * @return
	 */
	Review selectReview(int reviewNo);

	/**
	 * 리뷰 수정
	 * @param review
	 * @return
	 */
	int reviewUpdate(Review review);

}
