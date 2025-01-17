package edu.kh.bcs.point.service;

public interface PointService {
	
	
	/**
	 * 포인트 충전
	 * @param amount
	 * @param memberNo
	 * @return
	 */
	public int pointCharge(int amount, int memberNo);

	
	/**
	 * 포인트 충전 후 현재 포인트 조회
	 * @param memberNo
	 * @return
	 */
	public int selectMemberPoint(int memberNo);

}
