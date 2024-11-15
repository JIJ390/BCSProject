package edu.kh.bcs.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.bcs.admin.mapper.AdminMapper;
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
	public List<Member> getMemberList(int cp, String searchType, String searchText, int ud) {
		
		List<Member> memberList = null;
		
		if(searchType.equals("회원번호") && !searchText.equals("")) {
				memberList = mapper.memberNoList(searchText);
		}
		else if(searchType.equals("이름") && !searchText.equals("")){
			if(ud == 1) {
				memberList = mapper.memberNameListU(cp, searchText);
			}
			else {
				memberList = mapper.memberNameListD(cp, searchText);
				
			}
		}
		else if(searchType.equals("이메일") && !searchText.equals("")) {
			if(ud == 1) {
				memberList = mapper.memberEmailListU(cp, searchText);
			}
			else {
				memberList = mapper.memberEmailListD(cp, searchText);
			}
		}
		else if(searchType.equals("전화번호") && !searchText.equals("")) {
			if(ud == 1) {
				memberList = mapper.memberTelListU(cp, searchText);
			}else {
				memberList = mapper.memberTelListD(cp, searchText);
			}
			
		}
		else {
			if(ud == 1) {
				memberList = mapper.allListU(cp);
			}
			else {
				memberList = mapper.allListD(cp);
			}
		}
		
		
		return memberList;
	}

	
	@Override
	public List<Device> deviceList() {

		
		return mapper.deviceList();
	}
}

