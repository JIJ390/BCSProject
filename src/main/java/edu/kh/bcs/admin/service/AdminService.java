package edu.kh.bcs.admin.service;

import java.util.List;

import org.springframework.ui.Model;

import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.myPage.dto.Member;

public interface AdminService {

	int getResultCount(String searchType, String searchText);

	List<Member> getMemberList(int cp, String searchType, String searchText, int ud);
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








}
