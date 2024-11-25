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
import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.common.util.FileUtil;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.Grade;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:/config.properties")
public class AdminServiceImpl implements AdminService {

	private final AdminMapper mapper;

	// 디바이스 메인 사진
	@Value("${my.device.folder-path}")
	private String folderPathDevice;
	@Value("${my.device.web-path}")
	private String webPathDevice;

	// 디바이스 단일 색
	@Value("${my.deviceColor.folder-path}")
	private String folderPathColor;
	@Value("${my.deviceColor.web-path}")
	private String webPathDeviceColor;

	@Override
	public int getResultCount(String searchType, String searchText) {

		int resultCount = 0;

		if (searchType.equals("회원번호") && !searchText.equals("")) {
			resultCount = mapper.memberNoCount(searchText);
		} else if (searchType.equals("이름") && !searchText.equals("")) {
			resultCount = mapper.memberNameCount(searchText);
		} else if (searchType.equals("이메일") && !searchText.equals("")) {
			resultCount = mapper.memberEmailCount(searchText);
		} else if (searchType.equals("전화번호") && !searchText.equals("")) {
			resultCount = mapper.memberTelCount(searchText);
		} else {
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

	// 디바이스 리스트 조회

	@Override
	public Map<String, String> adminMemberDetail(int memberNo) {

		Map<String, String> map = new HashMap<>();
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
	public ChattingRoomDto chatroom(int chattingRoomNo) {
		return mapper.chatroom(chattingRoomNo);
	}
	
	@Override
	public int createChatRoom(int memberNo) {
		
		// 이미 있는지 조회
		if(mapper.checkRoomNo(memberNo) > 0) {
			return mapper.selectRoomNo(memberNo);
		}
		
		// 관리자 13번이 눌렀는지 조회
		if(mapper.checkRoomFl(memberNo)) {
			return 0;
		}

		// 채팅방 만들기
		int create = mapper.createChatRoom(memberNo);
		
		// 채팅방 생성 실패
		if(create < 1) {
			return 0;
		}
		
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
	
	@Override
	public List<Device> galaxyA(String series) {
		
		if(series.equals("A")) {
			return mapper.galaxyA();
		}
		if(series.equals("S")) {
			return mapper.galaxyS();
		}
		if(series.equals("Filp")) {
			return mapper.galaxyFilp();
		}
		else if(series.equals("Fold")) {
			
			return mapper.galaxyFold();
		}
		else {
			return mapper.galaxyTab();
		}
		
	}
	
	@Override
	public List<Device> iPhone(String series) {
		if(series.equals("number")) {
			return mapper.iPhone();
		}
		if(series.equals("X")) {
			return mapper.iPhoneX();
		}
		else {
			return mapper.iPad();
		}
	}


	// ============================================기종 등록
	@Override
	public int textContent(Device device, Color color, 
			String gradeType, String gradePrice, String gradeSellPrice, List<MultipartFile> colorImg,
			MultipartFile divceImg) {
		// device파일 리네임
		String divceImge = divceImg.getOriginalFilename();

		String deviceRename = FileUtil.rename(divceImge);

		device.setDeviceImg(webPathDevice + deviceRename);

		// 스펙 등록
		int deviceInsert = mapper.device(device);

		// insert 실패할경우
		if (deviceInsert == 0) {
			System.out.println("상세 정보에 값이 제대로 들어오지 않았습니다.");
			// 0 주고 탈출
			return 0;
		} else {
			try {

				File folder = new File(folderPathDevice);
				if (folder.exists() == false) { // 존재하지 않을때에
					folder.mkdir(); // 폴더 생성 구문
				}

				divceImg.transferTo(new File(folderPathDevice + deviceRename));

			} catch (Exception e) {
				e.printStackTrace();
			}

			// SELECT해서 DEVICE_NO 가져오기
			int deviceGetNo = mapper.selectDeviceNo();

			// colorImg 6개 사진 들어감
			// colorimg 필터
			for (int i = 0; i < colorImg.size(); i++) {
				// 없을 경우
				// 분기문중 없는 경우 다음 숫자
				if (colorImg.get(i).isEmpty())
					continue;

				// 이미지가 있을 경우 원본명 얻어오기
				String textImg = colorImg.get(i).getOriginalFilename();

				// 변경된 파일명 중복되지않게끔
				// 만들어둔 FileUtil 사용하기
				String rename = FileUtil.rename(textImg);

				String[] colorNameList = color.getColorName().split(",");
				String[] colorCodeList = color.getColorCode().split(",");
				String colorNameOut = colorNameList[i];
				String colorCodeOut = colorCodeList[i];

				//
				Color color2 = new Color();

				// deviceCode 빨간색
				color2.setColorName(colorNameOut);
				// deviceCode #23123
				color2.setColorCode(colorCodeOut);
				// deviceGetNo select 해서 가져온 값
				color2.setDeviceNo(deviceGetNo);
				color2.setColorDeviceImg(webPathDeviceColor + rename);

				try {
					int colorInsert = mapper.colorInsert(color2);

					File folder = new File(folderPathColor);
					if (folder.exists() == false) { // 존재하지 않을때에
						folder.mkdir(); // 폴더 생성 구문
					}
					colorImg.get(i).transferTo(new File(folderPathColor + rename));

				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
			} // for 문 끝남
			
			
			String[] gradeTypeSplit = gradeType.split(",");
			String[] gradePriceSplit = gradePrice.split(",");
			String[] gradeSellPriceSplit = gradeSellPrice.split(",");
				
			//3개
			for(int i = 0 ; i < gradeTypeSplit.length; i++) {
				
				//자른거
				
				//자른거 완성
				String gradePriceOrly = gradePriceSplit[i];
				String gradeSellPriceOrly = gradeSellPriceSplit[i];
				String GradeTypeOrly = gradeTypeSplit[i];
				
				
				int grade = mapper.grade(gradePriceOrly,gradeSellPriceOrly,GradeTypeOrly,deviceGetNo);
				
				
			}
			

			
			
			
			
		}
		
			return 1;
	}

}
