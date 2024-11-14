package edu.kh.bcs.admin.service;

import java.util.List;

import org.springframework.ui.Model;

import edu.kh.bcs.myPage.dto.Member;

public interface AdminService {

	int getResultCount(String searchType, String searchText);

	List<Member> getMemberList(int cp, String searchType, String searchText, int ud);

}
