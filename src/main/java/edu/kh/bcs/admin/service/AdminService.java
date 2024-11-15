package edu.kh.bcs.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

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

}
