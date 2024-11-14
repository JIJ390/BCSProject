package edu.kh.bcs.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.bcs.myPage.dto.Member;

@Mapper
public interface MyPageMapper {

	/** memberId가 일치하는 회원 정보조회
	 * @param memberId
	 * @return loginMember 또는 null
	 */
	Member login(String memberId);

}
