package edu.kh.bcs.review.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.point.dto.Point;
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

	
	

	
	/**
	 * 리뷰 작성 시 적립할 포인트 얻어오기
	 * @param orderNo
	 * @return
	 */
	int selectPlusPoint(int orderNo);
	
	
	/**
	 * 리뷰 작성 시 포인트 적립
	 * @param point
	 * @return
	 */
	int pointChange(Point point);

	/**
	 * 포인트 로그
	 * @param point
	 * @return
	 */
	int insertPointLog(Point point);

	
	/**
	 * 현재 회원 포인트 가져오기
	 * @param memberNo
	 * @return
	 */
	int selectMemberPoint(int memberNo);


}
