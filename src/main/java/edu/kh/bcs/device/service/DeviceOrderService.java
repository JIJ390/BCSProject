package edu.kh.bcs.device.service;

import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.point.dto.Point;

public interface DeviceOrderService {

	
	/**
	 * 구매 기종 정보 + 예상 가격 만들어 결제 페이지 이동
	 * @param buyingDevice
	 * @return
	 */
	BuyingDevice orderDeviceView(BuyingDevice buyingDevice);

	
	/**
	 * 주문 정보 받아서 주문 insert + 결제 완료 페이지 이동
	 * @param order
	 * @return
	 */
	int orderDevicePayment(Order order);

	/**
	 * 주문 번호로 주문 기종 정보 가져오기
	 * @param orderNo
	 * @return
	 */
	Order selectOrder(int orderNo);


	/**
	 * 포인트 변동사항 불러오기
	 * @param memberNo
	 * @return
	 */
	Point selectPointLog(int orderNo);


	
	/**
	 * 변동 후 포인트 세팅을 위한 정보
	 * @param memberNo
	 * @return
	 */
	int selectCurrnetPoint(int memberNo);

}
