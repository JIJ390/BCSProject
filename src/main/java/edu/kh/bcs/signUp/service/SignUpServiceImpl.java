package edu.kh.bcs.signUp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.signUp.mapper.SignUpMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SignUpServiceImpl implements SignUpService{

	public final SignUpMapper mapper;
	
	@Autowired // BCrypt 암호화 객체 의존성 주입 받기
	private BCryptPasswordEncoder encoder;
	
	// 이메일 중복검사
	@Override
	public int emailCheck(String email) {
		return mapper.emailCheck(email);
	}
	
	// 아이디 중복검사
	@Override
	public int idCheck(String id) {
		
		return mapper.idCheck(id);
	}

	// 회원가입
	@Override
	public int signUpRun(Member inputMember) {
		
	// 비밀번호 암호화(BCrypt)
			String encPw = encoder.encode(inputMember.getMemberPw());
			log.debug("원본 : {}", inputMember.getMemberPw());
			
			inputMember.setMemberPw(encPw);
			log.debug("암호 : {}", encPw);
		
		
		return mapper.signUpRun(inputMember);
	}
	
}
