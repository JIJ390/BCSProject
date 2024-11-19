package edu.kh.bcs.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.myPage.service.MyPageService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class MyPageController {
	
	private final MyPageService service;
	
	
	/**
	 * @param memberId
	 * @param memberPw
	 * @param saveId : 아이디 저장 여부(체크 안하면 null)
	 * @param ra : 리다이렉트시 request scope로 값 전달하는 객체
	 * @param Model 데이터 전달용 객체(기본값 request scope)
	 * @param resp : 응답 방법을 제공하는 객체
	 */
	@PostMapping("myPage/login")
	public String login(
			@RequestParam("memberId")String memberId,
			@RequestParam("memberPw")String memberPw,
			@RequestParam(name="saveId",
										required=false)String saveId,
			RedirectAttributes ra,
			Model model,
			HttpServletResponse resp
			) {
		
//		log.debug("memberId : {}", memberId);
//		log.debug("memberId : {}", memberId);
//		log.debug("memberId : {}", memberId);
//		log.debug("memberId : {}", memberId);
//		log.debug("memberPw : {}", memberPw);
		
		// 로그인 서비스 호출
		Member loginMember = service.login(memberId, memberPw);
		
//		log.debug("loginMember {}", loginMember);
//		log.debug("loginMember {}", loginMember);
//		log.debug("loginMember {}", loginMember);
//		log.debug("loginMember {}", loginMember);
		
		
		if(loginMember == null) { // 로그인이 실패
			
//			log.debug("aaaaaaaaaa");
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다!");
			
			return "redirect:/myPage/myPageLogin";
		}else { // 로그인 성공
			
			// loginMember를 session scope에 추가
			// 방법 1) HttpSession 이용
			// 방법 2) @SessionAttributes + Model 이용방법
			
			/* Model을 이용해서 Session scope에 값 추가하는 방법 */
			// 1. model에 값 추가 (request)
			model.addAttribute("loginMember", loginMember);
			
			log.debug("loginMember", loginMember);
			
			// 2. 클래스 선언부에 @SessionAttributes({"key"}) 추가
			// -> key 값은 model에 추가된 값 "loginMember" 작성
			// (request -> session) 으로 바뀜
			
			// @SessionAttributes :
			// Model 에 추가된 값 중 session scope로 올리고 싶은 값의
			// key를 작성하는 어노테이션
			// ------------------------------------------
			/* 이메일 저장코드(Cookie)
			 *  
			 */
			
			// 1. Cookie 객체 생성(K:V)
			Cookie cookie = new Cookie("saveId", memberId);
			
			// 2. 만들어진 Cookie 사용된 경로(url)
			cookie.setPath("/"); // localhost 또는 현재 ip 이하 모든주소
			
			// 3. Cookie 가 유지되는 시간(수명) 설정
			if(saveId == null) { // 체크 X
				cookie.setMaxAge(0); // 만들어지자 마자 만료
					 // == 기존에 쿠키가 있으면 덮어씌우고 없어짐
				
			}else { // 체크 O
				cookie.setMaxAge(60 * 60 * 24 * 30); // 30일 초 단위로 작성
				
			}
			
			// 4. resp 객체에 추가해서 클라이언트에게 전달
			resp.addCookie(cookie);
			
			// ------------------------------------------
			
		}
		
		
		return "redirect:/"; // 메인 페이지 리다이렉트
	}
	
	/** 로그아웃 기능
	 * @param status
	 * @return
	 */
	@GetMapping("myPage/logout")
	public String logout(SessionStatus status) {
		/* SessionStatus
		 * - @SessionStatus를 이용해 등록된 객체(값)의 상태를
		 *   관리하는 객체
		 * 
		 * - SessionStatus.setComplete()
		 * 	-> 세션 상태 완료 == 없앰(만료)		 
		 * 
		 * */
		status.setComplete();
		
		
		return "redirect:/"; // 메인페이지
	}
	
	/**
	 * 아이디 뜨게하기
	 * @param memberEmail
	 * @return
	 */
	@ResponseBody
	@PostMapping("/myPage/findId")
	public String findId(
			@RequestBody String memberEmail) {
		
		
		return service.findId(memberEmail);
	}
	
	
	
	
	@GetMapping("myPage/myPageLogin")
	public String myPageLogin() {
		
		return "myPage/myPageLogin";
	}
	
	
	@GetMapping("myPage/myPageMain")
	public String myPageMain() {
		
		return "myPage/myPageMain";
	}
	
	
	@GetMapping("myPage/myPageUpdate")
	public String myPageUpdate() {
		
		return "myPage/myPageupdate";
	}
	
	@GetMapping("myPage/myPageOrderHistory")
	public String myPageOrderHistory() {
		
		return "myPage/myPageOrderHistory";
	}
	
	@GetMapping("myPage/myPageSalesHistory")
	public String myPageSalesHistory() {
		
		return "myPage/myPageSalesHistory";
	}
	
	@GetMapping("myPage/myPagePointHistory")
	public String myPagePointHistory() {
		
		return "myPage/myPagePointHistory";
	}
	
	
	//
	
	
}



/* 이메일 저장코드(Cookie) 
- 클라이언트 측(브라우저)에서 관리하는 데이터(파일형식)

- Cookie에는 만료기간, 데이터, 사용하는 사이트가 기록되어있음

- 클라이언트가 쿠키에 기록된 사이트로 요청보낼 때
  요청에 쿠키가 담겨져서 서버로 넘어감
  
- Cookie의 생성, 수정, 삭제는 Server가 관리
  저장은 Client가 함
  
- Cookie는 HttpServerResponse를 이용해서 생성,
  클라리언트에게 요청한다
*/