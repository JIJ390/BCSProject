package edu.kh.bcs.myPage.service;

import edu.kh.bcs.myPage.dto.Member;

public interface MyPageService {

	/** 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return loginMemer 또는 null(Id 또는 pw 불일치)
	 */
	Member login(String memberId, String memberPw);

}
