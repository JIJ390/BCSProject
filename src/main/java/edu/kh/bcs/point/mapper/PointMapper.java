package edu.kh.bcs.point.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface PointMapper {

	/**
	 * 포인트 충전
	 * @param amount
	 * @param memberNo
	 * @return
	 */
	int pointCharge(			
			@Param("amount") int amount, 
			@Param("memberNo") int memberNo);

	
	/**
	 * 포인트 내역 입력
	 * @param amount
	 * @param memberNo
	 * @return
	 */
	int insertPointLog(			
			@Param("amount") int amount, 
			@Param("memberNo") int memberNo);



	/**
	 * 잔액 조회
	 * @param memberNo
	 * @return
	 */
	int selectMemberPoint(int memberNo);
	
	
	

}
