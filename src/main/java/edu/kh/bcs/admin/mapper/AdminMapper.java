package edu.kh.bcs.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.SellingDevice;
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

	int checkRoomNo(int memberNo);

	boolean checkRoomFl(int memberNo);


}
