package edu.kh.bcs.sse.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import edu.kh.bcs.myPage.dto.Member;
import edu.kh.bcs.sse.service.SseService;

@RestController
public class SseController {
	
	@Autowired
	private SseService service;

	 // SseEmitter : 서버로 부터 메시지를 전달 받을
  //              클라이언트 정보를 저장한 객체 == 연결된 클라이언트

  // ConcurrentHashMap : 멀티스레드 환경에서 동기화를 보장하는 Map
  //  -> 한 번에 많은 요청이 있어도 차례대로 처리
  private final Map<String, SseEmitter> emitters
    = new ConcurrentHashMap<>(); // == 연결된 클라이언트 대기 명단
	
  /** 클라이언트 연결 요청처리 */
  @GetMapping("sse/connect")
  public SseEmitter sseConnect(
    @SessionAttribute("loginMember") Member loginMember) {

    // Map에 저장될 Key 값으로 회원 번호 얻어오기
    String clientId = loginMember.getMemberNo() + "";

    // SseEmitter 객체 생성
    // -> 연결 대기 시간 10분 설정(ms 단위)
    SseEmitter emitter = new SseEmitter(10 * 60 * 1000L);

    // 클라이언트 정보를 Map에 추가
    emitters.put(clientId, emitter);

    // 클라이언트 연결 종료 시 Map에서 제거
    emitter.onCompletion(() -> emitters.remove(clientId));

    // 클라이언트 타임 아웃 시 Map에서 제거
    emitter.onTimeout(() -> emitters.remove(clientId));

    return emitter;
  }
  
  @PostMapping("sse/send")
  public void sendNotification(
  		@RequestBody int pkNo,
  		@SessionAttribute("loginMember") Member loginMember
  		) {
  	
  	String clientId = pkNo + "";
  	
  	SseEmitter emitter = emitters.get(clientId);
  	
  	if(emitter != null) {
  		try {
  			emitter.send( loginMember.getMemberNo() + "회원이 알림 보냄");
  		} catch (Exception e) {
  			emitters.remove(clientId);
			}
  	}
  }
  
  @GetMapping("sse/searchNewList")
  public int searchNewList(
  		@SessionAttribute("loginMember") Member loginMember
  		) {
  	
  	int result = service.searchNewList(loginMember.getMemberNo());
  	 
  	return result;
      
  }
  
  
  
  
  
  
  
  
  
  
}
