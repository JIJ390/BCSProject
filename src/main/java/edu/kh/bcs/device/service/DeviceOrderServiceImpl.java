package edu.kh.bcs.device.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.mapper.DeviceOrderMapper;
import edu.kh.bcs.point.dto.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceOrderServiceImpl implements DeviceOrderService {
	
	private final DeviceOrderMapper mapper;

	
	
	//구매 기종 정보 + 예상 가격 만들어 결제 페이지 이동
	@Override
		public BuyingDevice orderDeviceView(BuyingDevice buyingDevice) {
		
			// 구매 정보 담기
			BuyingDevice orderBuyingDevice = mapper.orderDeviceView(buyingDevice);
			
			// 예상 가격 정보 담기
			orderBuyingDevice.setBuyingDevicePrice(mapper.oderDevicePrice(buyingDevice));
		
			return orderBuyingDevice;
		}
	
	
	/**
	 * 주문 정보 받아서 주문 insert + 결제 완료 페이지 이동
	 * 
	 * 1. 주문 고객 포인트 체크
	 * 
	 * 2. 주문 고객 포인트 내리기
	 * 
	 * 3. 주문 insert
	 * 
	 */
	@Override
	public int orderDevicePayment(Order order) {

		int memberNo = order.getMemberNo();
		
		// 주문 회원 잔여 포인트 조회
		int balancePoint = mapper.pointCheck(memberNo);
		
		// 선택 매물 가격 조회
		int price = mapper.priceCheck(order);
		
		if (balancePoint < price) {
			return -1;
		}
		
		price = 1;
		
		Point point = new Point();
		
		point.setAmount(price);
		point.setMemberNo(memberNo);
		
		// 주문 고객 포인트 체크
		
		// 주문 고객 포인트 내리기
		int result = mapper.pointChange(point);
		int result2 = mapper.insertPointLog(point);
		
		order.setPointLogNo(point.getPointLogNo());
		
		// 주문 insert
		int result3 = mapper.insertOrder(order);
		
		// 매물 번호 세팅
		int buyingDeviceNo = order.getBuyingDeviceNo();
		
		// 매물 정보 변경
		int result4 = mapper.updateBuyingDevice(buyingDeviceNo, price);
		
		return order.getOrderNo();
	}
	
	
	
	// 주문 정보 가져오기
	@Override
	public Order selectOrder(int orderNo) {
		// TODO Auto-generated method stub
		return mapper.selectOrder(orderNo);
	}

	// 포인트 변동사항 불러오기
	@Override
	public Point selectPointLog(int orderNo) {
		return mapper.selectPointLog(orderNo);
	}
}
