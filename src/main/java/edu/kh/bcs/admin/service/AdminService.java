package edu.kh.bcs.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;



import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.device.dto.Capacity;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.Grade;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.help.dto.EventDto;
import edu.kh.bcs.help.dto.MainBannerDto;
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
			String capacitySellPrice);



	
	


}
