package edu.kh.bcs.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.bcs.myPage.dto.Member;

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
	int findIdReal(@Param("email") String email,
					       @Param("name") String name);

	/** 비번찾기
	 * @param id
	 * @param name
	 * @return
	 */
	String findPw(
			@Param("id") String id, 
			@Param("name") String name);




}
