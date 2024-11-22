package edu.kh.bcs.email.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.bcs.common.util.RedisUtil;
import edu.kh.bcs.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("email")
public class EmailController {

	@Autowired // 의존성 주입
	public RedisUtil redisUtil;
	
	@Autowired // 의존성 주입
	public EmailService service;

	/** 인증번호 발송
	 * @param email : 입력된 이메일
	 * @return 성공 1, 실패 0
	 */
	@ResponseBody
	@PostMapping("sendAuthKey")
	public int sendAuthKey(
			@RequestBody String memberEmail
			) {
		
		return service.sendEmail("signUp", memberEmail);
	}


	/** 인증 번호 확인
	 * @param map : 입력받은 email, authKey 가 저장된 map
	 * 	HttpMessageConverter에 의해 JSON -> Map 자동 변환
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public boolean checkAuthKey(
			@RequestBody Map<String, String> map) {
		
		
		return service.checkAuthKey(map);
	}
	
	/** 인증 번호 확인
	 * @param map : 입력받은 id, authKey 가 저장된 map
	 * 	HttpMessageConverter에 의해 JSON -> Map 자동 변환
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkAuthKey2")
	public boolean checkAuthKey2(
			@RequestBody Map<String, String> map) {
		
	
//		System.out.println(map.get("id"));
//		System.out.println(map.get("id"));
//		System.out.println(map.get("id"));
//		System.out.println(map.get("id"));
//		System.out.println(map.get("authKey"));
//		System.out.println(map.get("authKey"));
//		System.out.println(map.get("authKey"));
		
		return service.checkAuthKey2(map);
	}
	
	/** 아이디 찾기
	 * @param memberName
	 * @param memberEmail
	 * @return
	 */
	@ResponseBody
	@PostMapping("emailName")
	public int findIdReal(
				@RequestBody Map<String, String> obj2
			) {
		log.debug("sdfsdf : {}", obj2);
//		System.out.println(obj2.get("email"));
//		System.out.println(obj2.get("email"));
//		System.out.println(obj2.get("email"));
//		System.out.println(obj2.get("name"));
//		System.out.println(obj2.get("name"));
//		System.out.println(obj2.get("email"));
		
		return service.findIdReal("idFind", obj2);
		
	}
	
	/** 비밀번호 찾기
	 * @param obj3
	 * @return
	 */
	@ResponseBody
	@PostMapping("emailPw")
	public int findPw(
			@RequestBody Map<String, String> obj3
			) {
		
//		System.out.println(obj3.get("id"));
//		System.out.println(obj3.get("id"));
//		System.out.println(obj3.get("id"));
		
		return service.findPw("pwFind", obj3);
	}
	
	/** 임시 비번 발송
	 * @return
	 */
	
	@ResponseBody
	@PostMapping("sendAuthKey3")
	public String sendAuthKey3(
			@RequestBody String id,
			RedirectAttributes ra) {
		
		int sendAuthKey3 = service.sendAuthKey3("tempPw", id);
				
		String message = null;
		String path = null;
		
		System.out.println(id);
		System.out.println(id);
		System.out.println(id);
		System.out.println(id);
		
		if(sendAuthKey3 == 1) {
			message = "해당 아이디를 가진 사용자를 찾을 수 없습니다.";
			path = "/";
		}else {
			message = "회원님의 이메일로 임시비밀번호를 발송드렸습니다.";
			path = "myPageLogin";
		}
				
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	
	
	
	
	
	
}




