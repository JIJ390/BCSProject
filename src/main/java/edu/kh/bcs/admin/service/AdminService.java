package edu.kh.bcs.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;



import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.dto.Capacity;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.Grade;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.reviewRNDto;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.help.dto.EventDto;
import edu.kh.bcs.help.dto.HelpDto;
import edu.kh.bcs.help.dto.MainBannerDto;
import edu.kh.bcs.location.dto.locationDto;
import edu.kh.bcs.myPage.dto.Member;

public interface AdminService {

	int textContent(Device device, Color color, String gradeType, String gradePrice,
			String gradeSellPrice, List<MultipartFile> colorImg, MultipartFile divceImg, String capacityNumber,
			String capacityPrice, String capacitySellPrice);


	
	int getResultCount(String searchType, String searchText);

	List<Member> getMemberList(int cp, String searchType, String searchText, int ud, String searchAsc);
	/** 휴대폰 리스트
	 * 
	 * @return
	 */
	List<Device> deviceList();


	/** 팝업 색 가져오기
	 * @param deviceNo 
	 * 
	 * @return
	 */
	List<Color> popUpData(int result);

	//검색 조건
	List<Device> adminSearch(String search);








	Map<String, String> adminMemberDetail(int memberNo);

	int memberDelFlChange(int memberNo);
	int memberFlChange(int memberNo);


	/** 관리자 상품 조회
	 * 
	 * @param device
	 * @param color
	 * @return
	 */
	List<Device> result(Device device, Color color);

	Member getLoginMember(int memberNo);

	List<ChattingRoomDto> adminChatCheck(int memberNo);
 
	List<ChattingMessage> adminChattingList(int chattingRoomNo);

	ChattingRoomDto chatroom(int chattingRoomNo);

	int createChatRoom(int memberNo);

	int noReadCount(int memberNo);

	int chatRead(int chattingRoomNo, int memberNo);

	int firstArCheck(int memberNo);

	/** 이미지 내용 전달
	 * 
	 * @param deviceText divce 내용
	 * @param colorText	color 내용
	 * @param Grade Grade 내용
	 * @param divceImg img 1 개 
	 * @param colorImg img 6 개 
	 * @return
	 */

	List<Device> galaxyA(String series);

	List<Device> iPhone(String series);


	//리스트 조회
	List<Order> adminSale(String deviceNo);

	//상태 업데이트
	int delivery(int orderNo, int orderStatusCode);

	//검색 기능 필터
	List<Order> serachFilter(String searchResult);

	//상품조회 기기별 보기 버튼
	List<Device> brandFilter(String brandFilter);

	

	//adminSale 처음 화면 전환
	List<Order> adminSaleFirst();



	//update
	Map<String, Object> reload(String deviceNo);





	List<EventDto> getEventList();

	int updateBanner(MainBannerDto banner1, MainBannerDto banner2, MainBannerDto banner3, MainBannerDto banner4,
			MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4);

	int geteventListCount();

	List<EventDto> getEventList(int cp);

	int eventImgUpdate(MultipartFile img, int eventNo);

	int eventTitleUpdate(String eventTitle, int eventNo);

	int eventContentUpdate(String eventContent, int eventNo);

	String eventFlUpdate(int eventNo);


//업데이트
	int textContentUpdate(Device device, Color color, String gradeType, String gradePrice, String gradeSellPrice,
			List<MultipartFile> colorImg, MultipartFile divceImg, String capacityNumber, String capacityPrice, 
			String capacitySellPrice, String colorStatus, String colorNoCode);



	//전체 매물 목록 조회
	List<BuyingDevice> selectBuyingDeviceList();



	// adminAllList 검색
	List<BuyingDevice> adminAllListSearch(String search);


	//ProductinquirySearch
	List<Device> productinquirySearch(String search);



	int insertReviewNoti(String orderNo, String memberNo);



	String uploadImg(MultipartFile img);



	List<reviewRNDto> getOrderList(int memberNo);



	int deteleReviewRN(int orderNo);


	/** 브랜드명 찾기
	 * @param brandName
	 * @return
	 */
	List<Device> modelSelect(String brandName);



	/**
	 * 매물 등록
	 * @param newBuyingDevice
	 * @return
	 */
	int insertBuyingDevice(BuyingDevice newBuyingDevice);


	/** 전체 조회 총 개수
	 * 
	 * @param deviceNo
	 * @param search
	 * @return
	 */
	int result(int deviceNo, String search);



	List<Order> saleListSelect(int cp, int deviceNo, String search);


	/** adminProductinquiry전체 개수 조회
	 * 
	 * @param search
	 * @return
	 */
	int resultAll(String search);


	/** product rowNum
	 * 
	 * @param cp
	 * @param search
	 * @return
	 */
	List<Device> list(int cp, String search);



	List<SellingDevice> getBuyingList(String deviceNo, int cp, String searchText);



	int updateStatue(String sellingDeviceNo, String statusCode);



	int addBuyDevice(String deviceNo, String colorNo, String capacityNumber, String gradeNumber, String orderNo);



	int getDeviceResultCount(String searchText, String searchText2);



	/**
	 * 공지사항 목록 가져오기
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectNoticeList(int cp);



	/**
	 * 공지 사항 등록
	 * @param notice
	 * @return
	 */
	int adminNoticeInsert(HelpDto notice);



	/**
	 * 공지 사항 수정 화면 공지 불러오기
	 * @param noticeNumber
	 * @return
	 */
	HelpDto adminNoticeUpdateView(int noticeNumber);



	/**
	 * 공지 수정
	 * @param notice
	 * @return
	 */
	int adminNoticeUpdate(HelpDto notice);



	/**
	 * 공지 삭제 
	 * @param noticeNumber
	 * @return
	 */
	int adminNoticeDelete(int noticeNumber);


	/**
	 * 연간 총 매도 금액
	 * @return
	 */
	List<Map<String, String>> selectAnnualTotalSales();


	/**
	 * 연간 총 매입 금액
	 * @return
	 */
	List<Map<String, String>> selectAnnualTotalPurchases();



	// 매장 등록
	int locationInsert(locationDto location);



	
	


}
