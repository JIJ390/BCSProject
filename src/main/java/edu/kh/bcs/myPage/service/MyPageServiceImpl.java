package edu.kh.bcs.myPage.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.myPage.mapper.MyPageMapper;
import edu.kh.bcs.signUp.mapper.SignUpMapper;
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
	
	
	
	
}
