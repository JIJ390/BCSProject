package edu.kh.bcs.admin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.admin.mapper.AdminMapper;
import net.coobird.thumbnailator.Thumbnails;

import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.common.dto.Pagination;
import edu.kh.bcs.common.util.FileUtil;
import edu.kh.bcs.device.dto.BuyingDevice;
import edu.kh.bcs.device.dto.Capacity;
import edu.kh.bcs.device.dto.Color;
import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.device.dto.Grade;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.reviewRNDto;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.help.dto.EventDto;
import edu.kh.bcs.help.dto.HelpDto;
import edu.kh.bcs.help.dto.MainBannerDto;
import edu.kh.bcs.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

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
	
	@Value("${my.banner.web-path}")
	private String webPathBanner;
	@Value("${my.banner.folder-path}")
	private String folderPathBanner;
	
	// 채팅 이미지
	@Value("${my.chatting.web-path}")
	private String webPathchatting;
	@Value("${my.chatting.folder-path}")
	private String folderPathchatting;
	
	@Value("${my.event.web-path}")
	private String webPath;

	@Value("${my.event.folder-path}")
	private String folderPath;

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

	
	//관리자 - 상품조회
	@Override
	public List<Device> result(Device device, Color color) {
		
		List<Device> deviceList = mapper.deviceColorList(device,color);
		
		return deviceList;
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
	public int insertReviewNoti(String orderNo, String memberNo) {
		
		// 리뷰알림 있는지 조회
		int check = mapper.checkRN(orderNo, memberNo);
		
		// 리뷰 있으면
		if(check > 0) {
			return 0;
		}
		
		// 리뷰 없으면
		return mapper.insertReviewNoti(orderNo,memberNo);
	}
	
	
	
	
	@Override
	public String uploadImg(MultipartFile img) {
		
		String fileRename = FileUtil.rename(img.getOriginalFilename());
		
		try {

			File folder = new File(folderPathchatting);
			
			if (!folder.exists()) { // 존재하지 않을때에
				folder.mkdirs(); // 폴더 생성 구문

			} 

			img.transferTo(new File(folderPathchatting + fileRename));

		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		return webPathchatting + fileRename;
	}
	
	
	@Override
	public List<reviewRNDto> getOrderList(int memberNo) {
		
		return mapper.getOrderList(memberNo);
	}
	
	@Override
	public int deteleReviewRN(int orderNo) {
		return mapper.deleteReviewRN(orderNo);
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
	
	@Override
	public List<EventDto> getEventList() {
		return mapper.getEventLIst();
	}
	
	@Override
	public List<EventDto> getEventList(int cp) {
		return mapper.getEventLIstCp(cp);
	}
	
	@Override
	public int geteventListCount() {
		return mapper.geteventListCount();
	}
	
	@Override
	public int updateBanner(MainBannerDto banner1, MainBannerDto banner2, MainBannerDto banner3, MainBannerDto banner4,
			MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4) {
		
		String file1Path = FileUtil.rename(file1.getOriginalFilename());
		String file2Path = FileUtil.rename(file2.getOriginalFilename());
		String file3Path = FileUtil.rename(file3.getOriginalFilename());
		String file4Path = FileUtil.rename(file4.getOriginalFilename());
		
		banner1.setMainBannerImg(webPathBanner + file1Path);
		banner2.setMainBannerImg(webPathBanner + file2Path);
		banner3.setMainBannerImg(webPathBanner + file3Path);
		banner4.setMainBannerImg(webPathBanner + file4Path);
		
		int result1 = mapper.update1Banner(banner1);
		int result2 = mapper.update2Banner(banner2);
		int result3 = mapper.update3Banner(banner3);
		int result4 = mapper.update4Banner(banner4);
		
		if(result1 + result2 + result3 + result4 != 4) {
			return 0;
		}
		
		
		try {

			File folder = new File(folderPathBanner);

			
			
			
			if (!folder.exists()) { // 존재하지 않을때에
				folder.mkdirs(); // 폴더 생성 구문

			} 

			file1.transferTo(new File(folderPathBanner + file1Path));
			file2.transferTo(new File(folderPathBanner + file2Path));
			file3.transferTo(new File(folderPathBanner + file3Path));
			file4.transferTo(new File(folderPathBanner + file4Path));

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return result1+result2+result3+result4;
		
	}
	
	@Override
	public int eventImgUpdate(MultipartFile img, int eventNo) {
		
		
		String originalRename = FileUtil.rename(img.getOriginalFilename());
		String thumbRename = FileUtil.rename(img.getOriginalFilename());

		
		// DB 에 저장되는 경로
		String url1 = webPath + originalRename;
		String url2 = webPath + thumbRename;
		
		try {
    	
//    File originalImage = new File(folderPath + eventImage.getOriginalFilename());
    File originalImage = new File(folderPath + originalRename);
    originalImage.createNewFile();
      
    //  MultipartFile을 file 로 변환하기
    FileOutputStream fos = new FileOutputStream(originalImage);
    fos.write(img.getBytes());
    fos.close();
      
      File resizedImage = new File(folderPath + thumbRename);
      
      
  	// 원본 파일명 뒤에서 부터 검색해서 처음 찾은 "."의 index
	int index = img.getOriginalFilename().lastIndexOf(".") + 1;
	
	// 원본 파일명 "." 부터 끝까지 잘라낸 문자열 == .확장자
	String ext = img.getOriginalFilename().substring(index);
      
      
        // 이미지를 리사이즈하여 출력 경로에 저장
        Thumbnails.of(originalImage)
            .size(270, 200)  // 썸네일 크기 (너비 150px, 높이 150px)
            .outputFormat(ext)
            .toFile(resizedImage);
        

        System.out.println("Thumbnail created successfully!");

    } catch (IOException e) {
        System.err.println("Error creating thumbnail: " + e.getMessage());
        e.printStackTrace();
    }
		
		int result = mapper.eventUpdate(url1, url2, eventNo);
		
		if (result == 0) return 0;
//		try {
//			// C에 폴더가 없으면 생성
//			File folder = new File(folderPath);
//			if (!folder.exists())
//				folder.mkdirs();
//
//		
//			
//			// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
//			img.transferTo(new File(folderPath + originalRename));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return 0;
	}
	

	
	
	@Override
	public int eventTitleUpdate(String eventTitle, int eventNo) {
		return mapper.eventTitleUpdate(eventTitle, eventNo);
	}
	
	@Override
	public int eventContentUpdate(String eventContent, int eventNo) {
		return mapper.eventContentUpdate(eventContent, eventNo);
	}
	
	@Override
	public String eventFlUpdate(int eventNo) {
		
		int result = mapper.eventFlUpdate(eventNo);
		
		
		if(result > 0) {
			return mapper.eventFlSearch(eventNo);
		}
		
		return "";
	}
	
	

	@Override
	public List<SellingDevice> getBuyingList(String deviceNo, int cp, String searchText) {
		return mapper.getBuyingList(deviceNo,cp,searchText); 
	}
	
	
	@Override
	public int getDeviceResultCount(String deviceNo,String searchText) {
		
		return mapper.getDeviceResultCount(deviceNo,searchText);
	}
	
	
	
	@Override
	public int updateStatue(String sellingDeviceNo, String statusCode) {
		return mapper.updateStatue(sellingDeviceNo,statusCode);
	}
	
	
	@Override
	public int addBuyDevice(
			String deviceNo, String colorNo, String capacityNumber, 
			String gradeNumber, String orderNo) {
		
		// 있는지 체크
		int result = 0;
		result = mapper.checkBuyDevice(orderNo);
		
		// 있으면 리턴
		if(result == 4) {
			return 0;
		}
		
		result = mapper.addBuyDevice(deviceNo,colorNo,capacityNumber,gradeNumber);
		
		// 등록완료 상태코드로 변경
		mapper.statusChange(orderNo);
		
		return result;
	}
	
	
	
	
	
	
	
	

	// ============================================기종 등록
	@Override
	public int textContent(Device device, Color color, String gradeType, String gradePrice, String gradeSellPrice,
			List<MultipartFile> colorImg, MultipartFile divceImg, String capacityNumber, String capacityPrice,
			String capacitySellPrice){
		
		
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
					folder.mkdirs(); // 폴더 생성 구문
				}

				divceImg.transferTo(new File(folderPathDevice + deviceRename));

			} catch (Exception e) {
				e.printStackTrace();
			}

			// SELECT해서 DEVICE_NO 가져오기
			int deviceGetNo = mapper.selectDeviceNo();
			
			
			String[] colorNameList = color.getColorName().split(",");
			String[] colorCodeList = color.getColorCode().split(",");

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
						folder.mkdirs(); // 폴더 생성 구문
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
			
			//자르기
			String[] capacityNo1 = capacityNumber.split(",");
			String[] capacityPrice1 = capacityPrice.split(",");
			String[] capacitySellPrice1 = capacitySellPrice.split(",");
			
			
			
			for(int i = 0; i < capacityNo1.length ; i++) {
				
				String caNo = capacityNo1[i];
				String caPrice = capacityPrice1[i];
				String caSellPrice = capacitySellPrice1[i];
				
				//용량 인서트
				
				if (!caPrice.equals("null")) {
					int capacity = mapper.capacity(caNo,caPrice,caSellPrice,deviceGetNo);
				}
				
			}
			System.out.println("등록 완료");
		}
		
			return 1;
	}
	
	@Override
	public int textContentUpdate(Device device, Color color, String gradeType, String gradePrice, String gradeSellPrice,
			List<MultipartFile> colorImg, MultipartFile divceImg, String capacityNumber, String capacityPrice,
			String capacitySellPrice, String colorStatus, String colorNoCode) {
		
		// if 문 밖에서 선언
		String deviceRename = null;
		
		// device파일 리네임
		if (!divceImg.isEmpty()) {
			
			String divceImge = divceImg.getOriginalFilename();
			

			deviceRename = FileUtil.rename(divceImge);

			device.setDeviceImg(webPathDevice + deviceRename);
		}


		// 스펙 수정
		int deviceUpdate = mapper.deviceUpdate(device);

		// insert 실패할경우
		if (deviceUpdate == 0) {
			System.out.println("상세 정보에 값이 제대로 들어오지 않았습니다.");
			// 0 주고 탈출
			return 0;
		} else {
			try {

				File folder = new File(folderPathDevice);
				if (folder.exists() == false) { // 존재하지 않을때에
					folder.mkdirs(); // 폴더 생성 구문
				}
				
				if (!divceImg.isEmpty()) {
					
					divceImg.transferTo(new File(folderPathDevice + deviceRename));
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			

			String[] colorNameList = color.getColorName().split(",");
			String[] colorCodeList = color.getColorCode().split(",");
			String[] colorStatusList = colorStatus.split(",");
			String[] colorNoCodeList = colorNoCode.split(",");
			

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

				String colorNameOut = colorNameList[i];
				String colorCodeOut = colorCodeList[i];
				String colorStatusOut = colorStatusList[i];
				String colorNoCodeOut = colorNoCodeList[i];
				
				//
				Color color2 = new Color();

				// deviceCode 빨간색
				color2.setColorName(colorNameOut);
				// deviceCode #23123
				color2.setColorCode(colorCodeOut);
				// deviceGetNo select 해서 가져온 값
				color2.setColorDeviceImg(webPathDeviceColor + rename);

				color2.setColorNo(Integer.parseInt(colorNoCodeOut));
				//color = deviceNo
				color2.setDeviceNo(device.getDeviceNo());
				
				log.debug("colorStatusOut checkkkkk: {}", colorStatusOut);
				log.debug("colorStatusOut checkkkkk: {}", colorStatusOut);
				log.debug("colorStatusOut checkkkkk: {}", colorStatusOut);
				log.debug("colorStatusOut checkkkkk: {}", colorStatusOut);
				
				
				// 수정
				if (colorStatusOut.equals("1")) {
					
					
					try {
						int colorUpdate = mapper.colorUpdate(color2);

						File folder = new File(folderPathColor);
						if (folder.exists() == false) { // 존재하지 않을때에
							folder.mkdirs(); // 폴더 생성 구문
						}
						colorImg.get(i).transferTo(new File(folderPathColor + rename));

					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
					
					// 등록
				} else if (colorStatusOut.equals("2")) {
					
					try {
						int colorUpdate = mapper.colorInsert(color2);

						File folder = new File(folderPathColor);
						if (folder.exists() == false) { // 존재하지 않을때에
							folder.mkdirs(); // 폴더 생성 구문
						}
						colorImg.get(i).transferTo(new File(folderPathColor + rename));

					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
					
					
				} else {
					
					log.debug("check out: {}", colorStatusOut);
					continue;
				}

			} // for 문 끝남
			
			
			
			for (int i = 0; i < 6; i++) {
				if (colorStatusList[i].equals("3")) {
					
					String colorNoCodeOut = colorNoCodeList[i];
					int result = mapper.colorDelete(colorNoCodeOut);
				}

			}
			
			int deviceNo = device.getDeviceNo();
			
			String[] gradeTypeSplit = gradeType.split(",");
			String[] gradePriceSplit = gradePrice.split(",");
			String[] gradeSellPriceSplit = gradeSellPrice.split(",");
				
			//3개
			for(int i = 0 ; i < gradeTypeSplit.length; i++) {
				
				//자른거
				
				//자른거 완성
				String gradePriceOrly = gradePriceSplit[i];
				String gradeSellPriceOrly = gradeSellPriceSplit[i];
				String gradeTypeOrly = gradeTypeSplit[i];
				
				
				int gradeUpdate = mapper.gradeUpdate(gradePriceOrly,gradeSellPriceOrly, gradeTypeOrly, deviceNo);
			}
			
			
			
			
			
			
			
			//자르기
			String[] capacityNo1 = capacityNumber.split(",");
			String[] capacityPrice1 = capacityPrice.split(",");
			String[] capacitySellPrice1 = capacitySellPrice.split(",");
			
			int capacityDelete = mapper.capacityDelete(deviceNo);
			
			for(int i = 0; i < capacityNo1.length ; i++) {
				
				String caNo = capacityNo1[i];
				String caPrice = capacityPrice1[i];
				String caSellPrice = capacitySellPrice1[i];
				
				//용량 인서트
				if (!caPrice.equals("null")) {
				int capacityInsert = mapper.capacityInsert(caNo,caPrice,caSellPrice,deviceNo);
				}
			}
			System.out.println("등록 완료");
		}
		
			return 1;
	}
	
	
	
	
	
	
	
	@Override
	public List<Order> adminSale(String deviceNo) {
		
		log.debug("device번호 : {}",deviceNo);
		log.debug("device번호 : {}",deviceNo);
		log.debug("device번호 : {}",deviceNo);
		log.debug("device번호 : {}",deviceNo);
		log.debug("device번호 : {}",deviceNo);
		log.debug("device번호 : {}",deviceNo);
		
		
		return mapper.adminSale(deviceNo);
	}
	
	//상태 업데이트
	@Override
	public int delivery(int orderNo, int orderStatusCode) {
		
			int update = mapper.update(orderNo,orderStatusCode);
		
		return update;
	}
	
	@Override
	public List<Order> serachFilter(String searchResult) {
		
		List<Order> result = null;
		
			result = mapper.serachFilter(searchResult);
			
		
		return result;
	}
	
	@Override
	public List<Device> brandFilter(String brandFilter) {
		
		
		
		return mapper.brandFilter(brandFilter);
	}
	@Override
	public List<Order> adminSaleFirst() {
		
		
		List<Order> result = mapper.adminSaleFirst();
				
		
		
		return result;
	}
	
//	화면 리로드
	@Override
	public Map<String, Object> reload(String deviceNo) {
		
		Device device =  mapper.reloadDevice(deviceNo);
		
		List<Grade> grade =  mapper.reloadGrade(deviceNo);
		
		List<Color> color =  mapper.reloadColor(deviceNo);
		
		List<Capacity> capacityPrice =  mapper.reloadCapacityPrice(deviceNo);
		log.debug("serviceImpl : {}", grade);
		log.debug("serviceImpl : {}", color);
		log.debug("serviceImpl : {}", capacityPrice);

		
		
		
		
		
		
		Map<String, Object> result = new HashMap<>();
		
		
		result.put("device", device);
		result.put("grade", grade);
		result.put("color", color);
		result.put("capacityPrice", capacityPrice);
		
		
		
		
		
		return result;
	}
	

	@Override
	public List<BuyingDevice> selectBuyingDeviceList() {
		
		return mapper.selectBuyingDeviceList();
	}
	
	@Override
	public List<BuyingDevice> adminAllListSearch(String search) {
		
		return mapper.adminAllListSearch(search);
	}
	
	@Override
	public List<Device> productinquirySearch(String search) {
		
		return mapper.productinquirySearch(search);
	}

	// 모델명찾기
	@Override
	public List<Device> modelSelect(String brandName) {

		
		
		return mapper.modelSelect(brandName);
	}

  
	
	// 매물 등록
	@Override
	public int insertBuyingDevice(BuyingDevice newBuyingDevice) {
		return mapper.insertBuyingDevice(newBuyingDevice);
	}
	 
	
	//전체 조회 총 개수
	@Override
	public int result(int deviceNo, String search) {
		
		
		return mapper.result(deviceNo,search);
	}
	
	@Override
	public List<Order> saleListSelect(int cp, int deviceNo, String search) {
		
		
		
		return mapper.saleListSelect(cp, deviceNo, search);
	}
	//adminProductinquiry 전체 개수 조회
	@Override
	public int resultAll(String search) {
		
		return mapper.resultAll(search);
	}
	@Override
	public List<Device> list(int cp, String search) {
		
		return mapper.productList(cp, search);
	}
	
	
	
	
	
	
	
	
	// 공지사항 목록
	@Override
	public Map<String, Object> selectNoticeList(int cp) {
		int listCount = mapper.getNoticeListCount();
		
		Pagination pagination = new Pagination(cp, listCount);
			
		int limit = pagination.getLimit();
		int offset = (cp - 1)* limit;
			
		RowBounds rowBounds = new RowBounds(offset, limit);
			
		List<HelpDto> noticeList = mapper.selectNoticeList(rowBounds);
			
		Map<String, Object> map = 
					Map.of("noticeList", noticeList, "pagination", pagination);
				
		return map;
	}
	
	
	
	
	// 공지 사항 등록
	@Override
	public int adminNoticeInsert(HelpDto notice) {
		return mapper.adminNoticeInsert(notice);
	}
	
	
	// 공지 수정 화면 불러오기
	@Override
	public HelpDto adminNoticeUpdateView(int noticeNumber) {
		return mapper.adminNoticeUpdateView(noticeNumber);
	}
	
	
	// 공지 수정
	@Override
	public int adminNoticeUpdate(HelpDto notice) {
		return mapper.adminNoticeUpdate(notice);
	}
	
	
	// 공지 삭제
	@Override
	public int adminNoticeDelete(int noticeNumber) {
		return mapper.adminNoticeDelete(noticeNumber);
	}
	
	
	
	
	
	@Override
	public List<Map<String, String>> selectAnnualTotalSales() {
		return mapper.selectAnnualTotalSales();
	}
	
	
	@Override
	public List<Map<String, String>> selectAnnualTotalPurchases() {
		return mapper.selectAnnualTotalPurchases();
	}
	

}
