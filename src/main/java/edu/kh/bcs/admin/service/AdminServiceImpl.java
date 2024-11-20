package edu.kh.bcs.admin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.admin.mapper.AdminMapper;
import edu.kh.bcs.common.util.FileUtil;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:/config.properties")
public class AdminServiceImpl implements AdminService{
	
	private final AdminMapper mapper;

	@Value("${my.device.web-path}")
	private String webPath;
	
	@Value("${my.device.folder-path}")
	private String folderPath;
	
	
	
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


	//디바이스 리스트 조회


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
	public List<Device> deviceList() {

		
		return mapper.deviceList();
	}

	

// 팝업	
	@Override
	public List<Color> popUpData(int result) {
		
		return mapper.popUpData(result);
	}
	
	@Override
	public List<Device> adminSearch(String search) {
		
		List<Device> result = mapper.adminSearch(search);
		
		
		log.debug("impl 값 ㅣ : {}", result);
		log.debug("impl 값 ㅣ : {}", result);
		return result;
	}


	@Override
	public Member getLoginMember(int memberNo) {
		return mapper.getLoginMember(memberNo);
	}
	
	
	@Override
	public int textContent(Device device, Color color, List<MultipartFile> colorImg, MultipartFile divceImg) {
		
		//device파일 리네임
		String divceImge = divceImg.getOriginalFilename();
		String deviceRename = FileUtil.rename(divceImge);
		
		
		
		int deviceInsert = mapper.device(device,deviceRename);
		
		//insert 실패할경우
		if(deviceInsert == 0) {
			
			System.out.println("업로드 실패");
		}else {
			try {
				
				File folder = new File(folderPath); 
				if(folder.exists() == false) { //존재하지 않을때에
					folder.mkdir(); // 폴더 생성 구문
				}
				
				divceImg.transferTo(new File(folderPath + deviceRename));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
//		//실제 업로드될 파일
//		List<Color> uploadList = new ArrayList<>();
//		
//		//colorimg 필터
//		for(int i = 0 ; i < colorImg.size() ; i++) { 
//			
//			// 없을 경우
//			//분기문중 없는 경우 다음 숫자
//			if(colorImg.get(i).isEmpty()) continue;
//			
//			//이미지가 있을 경우 원본명 얻어오기
//			String textImg = colorImg.get(i).getOriginalFilename();
//			
//			//변경된 파일명 중복되지않게끔
//			//만들어둔 FileUtil 사용하기
//			String rename = FileUtil.rename(textImg);
//			
//			log.debug("변경된 이름 : {}", rename);
//			log.debug("변경된 이름 : {}", rename);
//			log.debug("변경된 이름 : {}", rename);
//			log.debug("변경된 이름 : {}", rename);
//			
//		}
		
		
		
		
		return deviceInsert;
	}
	
	
	

}

