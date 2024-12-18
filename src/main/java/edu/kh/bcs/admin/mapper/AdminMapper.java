package edu.kh.bcs.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
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
import edu.kh.bcs.myPage.dto.Member;

@Mapper
public interface AdminMapper {

	int memberNoCount(String searchText);

	int memberNameCount(String searchText);

	int memberEmailCount(String searchText);

	int memberTelCount(String searchText);

	int allCount();

	

	String adminMemberFl(int memberNo);

	String adminMemberAdFl(int memberNo);

	String adminMemberBuy(int memberNo);

	String adminMemberSell(int memberNo);


	List<Member> searchMemberList( 
			@Param("cp")int cp, 
			@Param("searchType")String searchType, 
			@Param("searchText")String searchText, 
			@Param("ud")int ud,
			@Param("searchAsc") String searchAsc);

	
	// 디바이스 리스트 조회
	List<Device> deviceList();

	// 팝업 리스트 색 조회
	List<Color> popUpData(int result);
	//검색 조건
	List<Device> adminSearch(String search);

	int memberDelFlChange(int memberNo);

	int memberFlChange(int memberNo);


	/** 관리자 상품 조회
	 *  
	 * @param device
	 * @param color
	 * @return
	 */
	List<Device> deviceColorList(Device device, Color color);

	Member getLoginMember(int memberNo);

	List<ChattingRoomDto> adminChatCheck(int memberNo);

	List<ChattingMessage> adminChattingList(int chattingRoomNo);

	ChattingRoomDto chatroom(int chattingRoomNo);

	int createChatRoom(int memberNo);

	int selectRoomNo(int memberNo);

	int noReadCount(int memberNo);

	int chatRead(@Param("chattingRoomNo")int chattingRoomNo, @Param("memberNo")int memberNo);

	int firstArCheck(int memberNo);
	
	
	/** divceImg content
	 * 
	 * @param deviceText
	 * @param divceImg
	 * @return
	 */
	int device(Device device);

	//color에 넣을 deviceNo 구해오기
	int selectDeviceNo();

	/** device insert 후 -> color insert
	 * 
	 * @param color
	 * @return
	 */
	int colorInsert(Color color);

	/** grade Insert
	 * 
	 * @param gradePriceOrly
	 * @param gradeSellPriceOrly
	 * @param gradeTypeOrly
	 * @param deviceGetNo 
	 * @return
	 */
	int grade(
			@Param("gradePriceOrly") String gradePriceOrly, 
			@Param("gradeSellPriceOrly") String gradeSellPriceOrly, 
			@Param("gradeTypeOrly") String gradeTypeOrly,  
			@Param("deviceGetNo") int deviceGetNo);

	List<Device> galaxyA();

	List<Device> galaxyS();

	List<Device> galaxyFilp();

	List<Device> galaxyFold();

	List<Device> galaxyTab();

	List<Device> iPhone();

	List<Device> iPhoneX();

	List<Device> iPad();


//구매 신청 리스트 조회
	List<Order> adminSale(String deviceNo);
	
	//배송 상태 업데이트
	int update(@Param("orderNo")int orderNo, 
			   @Param("orderStatusCode")int orderStatusCode);

	//검색 기능
	List<Order> serachFilter(String searchResult);

	//상품조회 기기 버튼별 검색 보기
	List<Device> brandFilter(String brandFilter);

	//구매 신청목록 화면 처음 조회
	List<Order> adminSaleFirst();

	//용량 인서트
	int capacity(
			@Param("caNo") String caNo, 
			@Param("caPrice") String caPrice, 
			@Param("caSellPrice") String caSellPrice,
			@Param("deviceGetNo") int deviceGetNo);

	//업데이트 select
	Device reloadDevice(String deviceNo);
	List<Grade> reloadGrade(String deviceNo);
	List<Color> reloadColor(String deviceNo);
	List<Capacity> reloadCapacityPrice(String deviceNo);



	int checkRoomNo(int memberNo);

	boolean checkRoomFl(int memberNo);

	List<EventDto> getEventLIst();

	int update1Banner(MainBannerDto banner1);
	int update2Banner(MainBannerDto banner2);
	int update3Banner(MainBannerDto banner3);
	int update4Banner(MainBannerDto banner4);

	int geteventListCount();

	List<EventDto> getEventLIstCp(int cp);

	int eventUpdate(@Param("url1")String url1, @Param("url2")String url2, @Param("eventNo")int eventNo);

	int eventTitleUpdate(@Param("eventTitle")String eventTitle, @Param("eventNo")int eventNo);

	int eventContentUpdate(@Param("eventContent")String eventContent, @Param("eventNo")int eventNo);

	int eventFlUpdate(int eventNo);

	String eventFlSearch(int eventNo);

	int checkRN(@Param("orderNo")String orderNo, @Param("memberNo") String memberNo);

	int insertReviewNoti(@Param("orderNo")String orderNo, @Param("memberNo") String memberNo);

	List<reviewRNDto> getOrderList(int memberNo);

	int deleteReviewRN(int orderNo);
	int deviceUpdate(
			Device device);
	
	int colorUpdate(Color color2);

	int gradeUpdate(
			@Param("gradePriceOrly") String gradePriceOrly, 
			@Param("gradeSellPriceOrly") String gradeSellPriceOrly,
			@Param("gradeTypeOrly") String gradeTypeOrly, 
			@Param("deviceNo") int deviceNo);


	
	
	
	
// 용량별 가격
	
	int capacityDelete(int deviceNo);
	
	int capacityInsert(
			@Param("caNo") String caNo, 
			@Param("caPrice") String caPrice, 
			@Param("caSellPrice") String caSellPrice, 
			@Param("deviceNo") int deviceNo);

	
	//color 사진/코드
	
	// 삭제
	int colorDelete(String colorNoCodeOut);

	//전체 매물 목록
	List<BuyingDevice> selectBuyingDeviceList();

	//adminAllList 검색 
	List<BuyingDevice> adminAllListSearch(String search);

	// productinquiry 검색
	List<Device> productinquirySearch(String search);





	/** 모델명찾기
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
	int result(
			@Param("deviceNo") int deviceNo, 
			@Param("search") String search);

	
	List<Order> saleListSelect(
			@Param("cp")int cp, 
			@Param("deviceNo")int deviceNo, 
			@Param("search")String search);

	/** adminProductinquiry 전체 개수 조회
	 * 
	 * @param search
	 * @return
	 */
	int resultAll(String search);

	/** product 로우 넘
	 * 
	 * @param cp
	 * @param search
	 * @return
	 */
	List<Device> productList(
			@Param("cp") int cp, 
			@Param("search") String search);

	List<SellingDevice> getBuyingList(
			@Param("deviceNo") String deviceNo, 
			@Param("cp") int cp,
			@Param("searchText") String searchText);

	int updateStatue(@Param("sellingDeviceNo") String sellingDeviceNo, @Param("statusCode") String statusCode);

	int checkBuyDevice(String orderNo);

	int addBuyDevice(
			@Param("deviceNo") String deviceNo, 
			@Param("colorNo")String colorNo, 
			@Param("capacityNumber")String capacityNumber, 
			@Param("gradeNumber")String gradeNumber);

	int statusChange(String orderNo);

	int getDeviceResultCount(@Param("deviceNo")String deviceNo, @Param("searchText")String searchText);

	
	
	
	/**
	 * 공지사항 목록 수
	 * @return
	 */
	int getNoticeListCount();

	
	/***
	 * 공지사항 목록
	 * @param rowBounds
	 * @return
	 */
	List<HelpDto> selectNoticeList(RowBounds rowBounds);

	
	/**
	 * 공지 등록
	 * @param notice
	 * @return
	 */
	int adminNoticeInsert(HelpDto notice);

	
	/**
	 * 공지	사항 수정 화면 불러오기
	 * @param noticeNumber
	 * @return
	 */
	HelpDto adminNoticeUpdateView(int noticeNumber);

	
	/**
	 * 공지 사항 수정
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

	
	
	List<Map<String, String>> selectAnnualTotalSales();

	
	List<Map<String, String>> selectAnnualTotalPurchases();




}
