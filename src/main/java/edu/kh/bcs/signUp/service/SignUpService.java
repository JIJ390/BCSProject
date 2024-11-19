package edu.kh.bcs.signUp.service;

import edu.kh.bcs.myPage.dto.Member;

public interface SignUpService {

	/** 이메일 중복검사
	 * @param email
	 * @return count
	 */
	int emailCheck(String email);

	/** 아이디 중복검사
	 * @param id
	 * @return count
	 */
	int idCheck(String id);

	/** 회원가입
	 * @param inputMember
	 * @return
	 */
	int signUpRun(Member inputMember);

}
