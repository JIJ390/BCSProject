package edu.kh.bcs.signUp.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.myPage.dto.Member;

@Mapper
public interface SignUpMapper {

	// 이메일 중복검사
	int emailCheck(String email);

	// 아이디 중복검사
	int idCheck(String id);

	// 회원가입
	int signUpRun(Member inputMember);


	
	
}
