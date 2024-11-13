package edu.kh.bcs.signUp.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignUpMapper {

	// 이메일 중복검사
	int emailCheck(String email);

	// 아이디 중복검사
	int idCheck(String id);

	
	
}
