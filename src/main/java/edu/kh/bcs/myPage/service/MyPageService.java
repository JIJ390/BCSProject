package edu.kh.bcs.myPage.service;

import edu.kh.bcs.myPage.dto.Member;

public interface MyPageService {

	/** 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return loginMemer 또는 null(Id 또는 pw 불일치)
	 */
	Member login(String memberId, String memberPw);

	/**
	 * 아이디 불러오기
	 * @param memberEmail
	 * @return
	 */
	String findId(String memberEmail);

	/** 회원탈퇴 기능
	 * @param memberPw
	 * @param loginMember
	 * @return
	 */
	int withdrawal(String memberPw, Member loginMember);

	/** 비번변경
	 * @param currentPw
	 * @param newPw
	 * @param loginMember
	 * @return
	 */
	int passwordChange(String currentPw, String newPw, Member loginMember);

}
