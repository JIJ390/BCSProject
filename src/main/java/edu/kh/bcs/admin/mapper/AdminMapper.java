package edu.kh.bcs.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.device.dto.Device;
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

	
	
	List<Device> deviceList();

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

}
