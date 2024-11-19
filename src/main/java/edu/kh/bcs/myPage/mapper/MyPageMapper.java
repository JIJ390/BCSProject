package edu.kh.bcs.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.myPage.dto.Member;
import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface MyPageMapper {

	/** memberId가 일치하는 회원 정보조회
	 * @param memberId
	 * @return loginMember 또는 null
	 */
	Member login(String memberId);

	/** 아이디 불러오기
	 * @param memberEmail
	 * @return
	 */
	String findId(String memberEmail);

	/** 아이디 찾기
	 * @param memberName
	 * @param memberEmail
	 * @return
	 */
	int findIdReal(
			@Param("memberName") String memberName, 
			@Param("memberEmail") String memberEmail);

}
