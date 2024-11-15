package edu.kh.bcs.email.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.bcs.common.util.RedisUtil;
import edu.kh.bcs.email.service.EmailService;

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
			@RequestBody String email) {
		
		return service.sendEmail("signUp", email);
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
	
	
	
	
	
	
	
	
	
	
	
}




