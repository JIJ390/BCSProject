package edu.kh.bcs.device.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.point.dto.Point;

@Mapper
public interface DeviceOrderMapper {

	
	/**
	 * 구매 기종 정보 담기
	 * @param buyingDevice
	 * @return
	 */
	BuyingDevice orderDeviceView(BuyingDevice buyingDevice);

	
	/**
	 * 예상 가격 조회
	 * @param buyingDevice
	 * @return
	 */
	int oderDevicePrice(BuyingDevice buyingDevice);


	/**
	 * 로그인 회원 포인트 조회
	 * @param memberNo
	 * @return
	 */
	int pointCheck(int memberNo);


	/**
	 * 가격 조회
	 * @param order
	 * @return
	 */
	int priceCheck(Order order);


	/**
	 * 구매 -> 포인트 감소
	 * @param point
	 * @return
	 */
	int pointChange(Point point);

	/**
	 * 구매 기록 남기기
	 * @param point
	 * @return
	 */
	int insertPointLog(Point point);


	/**
	 * 주문 정보 insert
	 * @param order
	 * @return
	 */
	int insertOrder(Order order);


	/**
	 * 매물 상태 판매 완료로 변경
	 * @param buyingDeviceNo
	 * @param price 
	 * @return
	 */
	int updateBuyingDevice(
			@Param("buyingDeviceNo") int buyingDeviceNo,
			@Param("price") int price);


	/**
	 * 주문 정보 가져오기
	 * @param orderNo
	 * @return
	 */
	Order selectOrder(int orderNo);


	/**
	 * 포인트 로그 가져오기
	 * @param memberNo
	 * @return
	 */
	Point selectPointLog(int orderNo);


	/**
	 * 현재 포인트
	 * @param memberNo
	 * @return
	 */
	int selectCurrnetPoint(int memberNo);

}
