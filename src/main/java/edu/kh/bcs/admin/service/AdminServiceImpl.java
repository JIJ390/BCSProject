package edu.kh.bcs.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.bcs.admin.mapper.AdminMapper;
import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.myPage.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	
	private final AdminMapper mapper;

	@Override
	public int getResultCount(String searchType, String searchText) {
		
		int resultCount = 0;
		
		if(searchType.equals("회원번호") && !searchText.equals("")) {
			resultCount = mapper.memberNoCount(searchText);
		}
		else if(searchType.equals("이름") && !searchText.equals("")){
			resultCount = mapper.memberNameCount(searchText);
		}
		else if(searchType.equals("이메일") && !searchText.equals("") ) {
			resultCount = mapper.memberEmailCount(searchText);
		}
		else if(searchType.equals("전화번호") && !searchText.equals("")) {
			resultCount = mapper.memberTelCount(searchText);
		}
		else {
			resultCount = mapper.allCount();
		}
		
		return resultCount;
	}
	
	@Override
	public List<Member> getMemberList(int cp, String searchType, String searchText, int ud, String searchAsc) {
		
		List<Member> memberList = null;
		

		
		memberList = mapper.searchMemberList(cp, searchType, searchText, ud, searchAsc);
		
		return memberList;
	}
	
	@Override
		public List<ChattingRoomDto> adminChatCheck(int memberNo) {
			return mapper.adminChatCheck(memberNo);
		}


	@Override
	public Map<String, String> adminMemberDetail(int memberNo) {
		
		Map<String , String> map = new HashMap<>();
		map.put("memberFl", mapper.adminMemberFl(memberNo));
		map.put("memberAdFl", mapper.adminMemberAdFl(memberNo));
		map.put("memberBuy", mapper.adminMemberBuy(memberNo));
		map.put("memberSell", mapper.adminMemberSell(memberNo));
		
		return map;
	}
	
	@Override
	public int memberDelFlChange(int memberNo) {
		
		return mapper.memberDelFlChange(memberNo);
	}
	@Override
	public int memberFlChange(int memberNo) {
		
		return mapper.memberFlChange(memberNo);
	}
	
	@Override
	public List<ChattingMessage> adminChattingList(int chattingRoomNo) {
		return mapper.adminChattingList(chattingRoomNo);
	}

	
	@Override
	public List<Device> deviceList() {

		
		return mapper.deviceList();
	}

	@Override
	public Member getLoginMember(int memberNo) {
		return mapper.getLoginMember(memberNo);
	}

	@Override
	public ChattingRoomDto chatroom(int chattingRoomNo) {
		return mapper.chatroom(chattingRoomNo);
	}
	
	@Override
	public int createChatRoom(int memberNo) {
		
		int create = mapper.createChatRoom(memberNo);
		int roomNo = mapper.selectRoomNo(memberNo);
		
		return roomNo;
	}
	
	@Override
	public int firstArCheck(int memberNo) {
		return mapper.firstArCheck(memberNo);
	}
	
	@Override
	public int noReadCount(int memberNo) {
		return mapper.noReadCount(memberNo);
	}
	
	@Override
	public int chatRead(int chattingRoomNo, int memberNo) {
		return mapper.chatRead(chattingRoomNo, memberNo);
	}
}

