package edu.kh.bcs.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import edu.kh.bcs.admin.mapper.AdminMapper;
import edu.kh.bcs.myPage.dto.Member;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminMapper mapper;

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
		
		memberList = mapper.searchMemberList(cp, searchType, searchText, ud);
		
		return memberList;
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
	
}
