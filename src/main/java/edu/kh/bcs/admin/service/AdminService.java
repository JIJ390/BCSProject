package edu.kh.bcs.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.myPage.dto.Member;

public interface AdminService {

	int getResultCount(String searchType, String searchText);

	List<Member> getMemberList(int cp, String searchType, String searchText, int ud, String searchAsc);
	/** 휴대폰 리스트
	 * 
	 * @return
	 */
	List<Device> deviceList();



	Map<String, String> adminMemberDetail(int memberNo);

	int memberDelFlChange(int memberNo);
	int memberFlChange(int memberNo);

	Member getLoginMember(int memberNo);

	List<ChattingRoomDto> adminChatCheck(int memberNo);

	List<ChattingMessage> adminChattingList(int chattingRoomNo);

	ChattingRoomDto chatroom(int chattingRoomNo);

	int createChatRoom(int memberNo);

	int noReadCount(int memberNo);

	int chatRead(int chattingRoomNo, int memberNo);

	int firstArCheck(int memberNo);

}
