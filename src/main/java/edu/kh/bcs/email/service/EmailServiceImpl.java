package edu.kh.bcs.email.service;

import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.bcs.common.util.RedisUtil;
import edu.kh.bcs.myPage.mapper.MyPageMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * 이메일 관련 비즈니스 로직 처리클래스
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	// EmailConfig 내용이 적용된 이메일 발송이 가능한 객체(Bean)
	private final JavaMailSender mailSender;

	// Redis(InMemory DB) CRUD 할 수 있는 기능을 제공하는 객체(Bean)
	private final RedisUtil redisUtil;

	// 자바 타임리프를 사용할 수 있게하는 객체(Bean)
	// html 코드를 java로 읽어올 수 있음
	private final SpringTemplateEngine templateEngine;
	
	private final MyPageMapper mapper;
	
	
	// 아이디 찾기
	@Override
	public int findIdReal(String memberName, String memberEmail) {
		return mapper.findIdReal(memberName, memberEmail);
	}
	
	// 이메일 발송 서비스
	@Override
	public int sendEmail(String htmlName, String email) {

		try {

			String emailTitle = null; // 발송되는 이메일 제목
			String authKey = createAuthKey(); // 생성된 인증번호

			// 이메일 발송 시 사용할 html 파일의 이름
			// 이메일 제목, 내용을 다르게 설정
			switch (htmlName) {
			case "signUp":
				emailTitle = "[BCS] 회원가입 인증번호 입니다.";
				break;

			case "findPw":
				emailTitle = "[BCS] 비밀번호 찾기 인증번호 입니다.";
				break;

			case "findId":
				emailTitle = "[BCS] 아이디 찾기 인증번호 입니다.";
				break;
			}

			/* 메일 발송 */

			// MimeMessage : 메일 발송 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			// MimeMessageHelper :
			// Spring 에서 제공하는 메일 발송 도우미

			// 매개변수 1 : MimeMessage
			// 매개변수 2 : 이메일에 파일첨부 여부
			// 매개변수 3 : 발송되는 이메일의 문자 인코딩 지정
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setTo(email); // 받는 사람 이메일 세팅
			helper.setSubject(emailTitle); // 이메일 제목 세팅
			helper.setText(loadHtml(authKey, htmlName), true); // 이메일 내용 세팅
			// 매개변수 1 : 이메일 내용
			// 매개변수 2 : HTML 코드 해석여부 지정(true == 해석 0)

			// 지정된 HTML 파일에 authKey 가 첨부된 후
			// HTML 코드 전체가 하나의 String 으로 변환되서 반환 받은 후
			// 변환된 String을 메일 내용으로 세팅

			// CID(Content-ID)를 이용해 메일에 이미지 첨부
			helper.addInline("logo", new ClassPathResource("static/images/sample_iphone16.jpg"));

			// 메일 발송하기
			mailSender.send(mimeMessage);

			// Redis에 이메일, 인증번호 저장(5분후 만료)
			redisUtil.setValue(email, authKey, 60 * 5);

		} catch (Exception e) {
			e.printStackTrace();
			return 0; // 예외 발생 == 실패 == 0 반환

		}

		return 1; // 예외 발생 X == 성공 == 1 반환
	}

	/**
	 * 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
	 * 
	 * @return authKey
	 */
	public String createAuthKey() {
		String key = "";
		for (int i = 0; i < 6; i++) {

			int sel1 = (int) (Math.random() * 3); // 0:숫자 / 1,2:영어

			if (sel1 == 0) {

				int num = (int) (Math.random() * 10); // 0~9
				key += num;

			} else {

				char ch = (char) (Math.random() * 26 + 65); // A~Z

				int sel2 = (int) (Math.random() * 2); // 0:소문자 / 1:대문자

				if (sel2 == 0) {
					ch = (char) (ch + ('a' - 'A')); // 대문자로 변경
				}

				key += ch;
			}
		}
		return key;
	}

//HTML 파일을 읽어와 String으로 변환 (타임리프 적용)
	public String loadHtml(String authKey, String htmlName) {

		// org.tyhmeleaf.Context 선택!!
		Context context = new Context();

		// 타임리프가 적용된 HTML에서 사용할 값 추가
		context.setVariable("authKey", authKey);

		// templates/email 폴더에서 htmlName과 같은
		// .html 파일 내용을 읽어와 String으로 변환
		return templateEngine.process("email/" + htmlName, context);

	}

	// 인증번호 확인
	@Override
	public boolean checkAuthKey(Map<String, String> map) {
		
		// map에 저장된 값 꺼내오기
		String email = map.get("email");
		String authKey = map.get("authKey");
		
		// 1) Redis에 key 가 입력된 email과 같은 데이터가 있는지 확인
		if(redisUtil.hasKey(email) == false) { // 없을 경우
			return false;
		}
		
		
		return redisUtil.getValue(email).equals(authKey);
	}
	

	
	
	

}
