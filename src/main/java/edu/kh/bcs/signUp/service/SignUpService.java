package edu.kh.bcs.signUp.service;

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

}
