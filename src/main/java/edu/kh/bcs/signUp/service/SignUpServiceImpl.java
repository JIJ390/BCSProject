package edu.kh.bcs.signUp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.bcs.signUp.mapper.SignUpMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService{

	public final SignUpMapper mapper;
	
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
	
}
