package edu.kh.bcs.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

	List<Member> memberNoList(String searchText);

	List<Member> memberNameListU(@Param("cp") int cp, @Param("searchText") String searchText);
	List<Member> memberNameListD(@Param("cp") int cp, @Param("searchText") String searchText);

	List<Member> memberEmailListU(@Param("cp") int cp, @Param("searchText") String searchText);
	List<Member> memberEmailListD(@Param("cp") int cp, @Param("searchText") String searchText);

	List<Member> memberTelListU(@Param("cp") int cp, @Param("searchText") String searchText);
	List<Member> memberTelListD(@Param("cp") int cp, @Param("searchText") String searchText);

	List<Member> allListU(int cp);
	List<Member> allListD(int cp);

	
	// 디바이스 리스트 조회
	List<Device> deviceList();
	// 팝업 리스트 색 조회
	List<SellingDevice> popUpData();

}
