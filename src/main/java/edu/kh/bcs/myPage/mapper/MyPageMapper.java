package edu.kh.bcs.myPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.SellingDevice;
import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.point.dto.Point;

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
	int findIdReal(@Param("email") String email);

	/** 비번찾기
	 * @param id
	 * @param name
	 * @return
	 */
	String findPw(
			@Param("id") String id, 
			@Param("name") String name);

	/** 비번 인증
	 * @param id
	 * @return
	 */
	String checkAuthKey2(String id);

	/** 임시 비번
	 * @param id
	 * @return
	 */
	String sendAuthKey3(String id);

	/** 이메일을 통한 회원넘버 조회
	 * @param email
	 * @return
	 */
	int findMemberNoByEmail(String email);

	/** 회원넘버를 통한 비번 업데이트
	 * @param memberNo
	 * @param encPw
	 * @return
	 */
  int updatePassword(
  		@Param("memberNo") int memberNo, 
  		@Param("encPw") String encPw); // 비밀번호 업데이트

  /** 회원 탈퇴
   * @param memberNo
   * @return
   */
	int withdrawal(int memberNo);

	/** 비밀번호 변경
	 * @param memberNo
	 * @param encPw
	 * @return
	 */
	int passwordChange(
									@Param("memberNo")	int memberNo,
									@Param("encPw")	String encPw);

	/**
	 * 주소변경
	 * @param postcode
	 * @param memberNo
	 * @return
	 */
	int addressChange(
							@Param("address") String address, 
							@Param("memberNo") int memberNo);

	/** 번호변경
	 * @param number
	 * @param memberNo
	 * @return
	 */
	int numberChange(
							@Param("number") String number, 
							@Param("memberNo") int memberNo);

	/** 게시물 수
	 * @return
	 */
	int selectSellingListCount(int memberNo);
	
	/** 내폰 판매내역
	 * @param memberNo
	 * @param rowBounds
	 * @return
	 */
	List<SellingDevice> selectSellingList(int memberNo, RowBounds rowBounds);
	
	/**
	 *  게시물 수
	 * @return
	 */
	int selectPointListCount(int memberNo);

	/** 포인트 내역
	 * @param memberNo
	 * @param rowBounds
	 * @return
	 */
	List<Point> selectPointList(int memberNo, RowBounds rowBounds);

	/** 게시물 수
	 * @return
	 */
	int selectBuyingListCount(int memberNo);

	/** 구매 내역
	 * @param memberNo
	 * @param rowBounds
	 * @return
	 */
	List<Order> selectBuyingList(int memberNo, RowBounds rowBounds);
	




}
