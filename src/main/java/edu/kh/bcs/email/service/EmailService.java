package edu.kh.bcs.email.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 발송 서비스
	 * @param htmlName
	 * @param email
	 * @return
	 */
	int sendEmail(String htmlName, String email);
	


	/** 인증번호 확인
	 * @param map
	 * @return
	 */
	boolean checkAuthKey(Map<String, String> map);


	/** 아이디 찾기 
	 * @param obj2
	 * @return
	 */
	int findIdReal(String htmlName, Map<String, String> obj2);


	/** 비밀번호 찾기
	 * @param string
	 * @param obj3
	 * @return
	 */
	int findPw(String htmlName, Map<String, String> obj3);


 /** 인증번호 확인
  * @param map 
  * @return
  */
	boolean checkAuthKey2(Map<String, String> map);


/** 임시 비밀번호 발송
 * @param string
 * @param id
 * @return
 */
int sendAuthKey3(String htmlName, String id);







	

	


}
