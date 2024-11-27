package edu.kh.bcs.myPage.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.bcs.common.dto.Pagination;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.myPage.mapper.MyPageMapper;
import edu.kh.bcs.point.dto.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

	private final MyPageMapper mapper;
	/** 비밀번호 암호화
	 * - 하는 이유 : 평문 상태로 비밀번호 저장하면 안됨!
	 * 
	 * - 아주 옛날 방식 : 데이터 -> 암호화, 암호화된 데이터 -> 복호화 -> 원본데이터
	 * 
	 */
	
	
	private final BCryptPasswordEncoder encoder;
	
	@Override
	public Member login(String memberId, String memberPw) {
		
		// 암호화 테스트
//		log.debug("memberPw : {}", memberPw);
//		log.debug("암호화단 memberPw :{}", encoder.encode(memberPw));
		
		// 1. memberId가 일치하는 회원의 정보를 DB에서 조회
		//	 (비밀번호 포함!)
		Member loginMember = mapper.login(memberId);
		
//		
//		log.debug("loginMember {}", loginMember);
//		log.debug("loginMember {}", loginMember);
//		log.debug("loginMember {}", loginMember);
//		log.debug("loginMember {}", loginMember);
		
		// 2. id가 일치하는 회원정보가 없을경우
		if(loginMember == null) return null;
		
		// 3. DB에서 조회된 비밀번호와, 입력받은 비밀번호가 같은지 확인
//		log.debug("비밀번호 일치? : {}",
//					encoder.matches(memberPw, loginMember.getMemberPw()));

		// 입력받은 비밀번호와 DB에서 조회된 비밀번호가 일치하지 않을 때
		if( !encoder.matches(memberPw, loginMember.getMemberPw())) {
			return null;
		}
		
		
		// 4. 로그인 결과 반환
		return loginMember;
	}
	
	// 아이디불러오기
	@Override
	public String findId(String memberEmail) {
		return mapper.findId(memberEmail);
	}
	
	// 회원탈퇴 기능
	@Override
	public int withdrawal(String memberPw, Member loginMember) {
		
	// 1) 비밀번호 일치 검사
		if(encoder.matches(memberPw, loginMember.getMemberPw()) == false) {
			return 0; // 다를경우 0 반환
		}
		
	// 2) 회원탈퇴 Mapper 호출 (update)
		
		return mapper.withdrawal(loginMember.getMemberNo());
	}
	
	// 비밀번호 변경
	@Override
	public int passwordChange(String currentPw, String newPw, Member loginMember) {
		if(encoder.matches(currentPw, loginMember.getMemberPw()) == false) { // 비밀번호가 일치하지 않을때
			return 0;
		} 
		
		String encPw = encoder.encode(newPw);
		
		loginMember.setMemberPw(encPw);
		return mapper.passwordChange(loginMember.getMemberNo(), encPw);
	}
	
	// 주소 변경
	@Override
	public int addressChange(String address, int memberNo) {
		return mapper.addressChange(address, memberNo);
	}
	
	// 번호 변경
	@Override
	public int numberChange(String number, int memberNo) {
		return mapper.numberChange(number, memberNo);
	}
	
	// 내폰판매
	@Override
	public Map<String, Object> selectSellingList(int cp, int memberNo) {
		
			  // 게시물 수
	     	int completedListCount = mapper.selectSellingListCount(memberNo);
	     
//	      log.debug("completedListCount : {}", completedListCount);
	      
	      Pagination pagination = new Pagination(cp, completedListCount, 10, 5);

	      // ex) 현재 페이지 2 - 1 = 1
	      // 1 * 5 = 5
	      // 해당 인덱스 부터 게시물 가져오는 값
	      int offset = (cp - 1) * pagination.getLimit();
	      
	      // 페이징 처리를 위해 제공하는 API
	      // offset: 조회 시작 위치를 지정 (0부터 시작)
	      // limit: 조회할 데이터의 개수를 지정
	      RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
	      
	      List<SellingDevice> selectSellingList = mapper.selectSellingList(memberNo, rowBounds);
	      
	      
	      Map<String, Object> map = Map.of("selectSellingList", selectSellingList, "pagination", pagination);
	      
//	      System.out.println(selectSellingList);
//	      System.out.println(pagination);
	      
		return map;
	}

	// 포인트 내역
	@Override
	public Map<String, Object> selectPointList(int cp, int memberNo) {
		
	  // 게시물 수
   	int selectPointListCount = mapper.selectPointListCount(memberNo);
   
//    log.debug("selectPointListCount : {}", selectPointListCount);
    
    Pagination pagination = new Pagination(cp, selectPointListCount, 10, 5);

    // ex) 현재 페이지 2 - 1 = 1
    // 1 * 5 = 5
    // 해당 인덱스 부터 게시물 가져오는 값
    int offset = (cp - 1) * pagination.getLimit();
    
    // 페이징 처리를 위해 제공하는 API
    // offset: 조회 시작 위치를 지정 (0부터 시작)
    // limit: 조회할 데이터의 개수를 지정
    RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
    
    List<Point> selectPointList = mapper.selectPointList(memberNo, rowBounds);
    
    
    Map<String, Object> map = Map.of("selectPointList", selectPointList, "pagination", pagination);
    
//    System.out.println(selectPointList);
//    System.out.println(pagination);
    
    return map;
	}
	
	// 내폰 구매내역
	@Override
	public Map<String, Object> selectBuyingList(int cp, int memberNo) {
	
		 // 게시물 수
   	int selectBuyingListCount = mapper.selectBuyingListCount(memberNo);
   
   	log.debug("selectBuyingListCount : {}", selectBuyingListCount);
   	
//   	System.out.println(memberNo);
//   	System.out.println(memberNo);
//   	System.out.println(memberNo);
//   	System.out.println(memberNo);
    log.debug("selectBuyingListCount : {}", selectBuyingListCount);
    
    Pagination pagination = new Pagination(cp, selectBuyingListCount, 10, 5);

    // ex) 현재 페이지 2 - 1 = 1
    // 1 * 5 = 5
    // 해당 인덱스 부터 게시물 가져오는 값
    int offset = (cp - 1) * pagination.getLimit();
    
    // 페이징 처리를 위해 제공하는 API
    // offset: 조회 시작 위치를 지정 (0부터 시작)
    // limit: 조회할 데이터의 개수를 지정
    RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
    
    List<Order> selectBuyingList = mapper.selectBuyingList(memberNo, rowBounds);
    
    log.debug("pagination : {}", pagination);
    log.debug("pagination : {}", pagination);
    log.debug("pagination : {}", pagination);
    log.debug("pagination : {}", pagination);
    
    
    Map<String, Object> map = Map.of("selectBuyingList", selectBuyingList, "pagination", pagination);
    
//    System.out.println(selectBuyingList);
//    System.out.println(pagination);
    
    return map;
	}
	
	
	
}
