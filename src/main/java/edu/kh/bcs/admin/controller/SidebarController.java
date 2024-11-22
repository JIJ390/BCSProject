package edu.kh.bcs.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.bcs.admin.service.AdminService;
import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.myPage.dto.Member;

@Controller
public class SidebarController {
	
	@Autowired
	private AdminService service;

	@RequestMapping("sidebarHome")
	public String getHomeContent() {
		return "common/sidebar/home";
	}
	
	@RequestMapping("sidebarChat")
	public String getChatContent() {
		return "common/sidebar/chat";
	}
	@RequestMapping("sidebarPro")
	public String getProContent() {
		return "common/sidebar/profile";
	}
	@RequestMapping("sidebarChatDetail")
	public String sidebarChatDetail() {
		return "common/sidebar/chatDetail";
	}
	
	@GetMapping("sidebar/chatting-admin-check")
	public String sidebarChatCheck(
			@RequestParam("memberNo") int memberNo,
			@SessionAttribute("loginMember") Member loginMember,
			Model model
			) {
		
		List<ChattingRoomDto> chatList = service.adminChatCheck(memberNo);
		model.addAttribute("chatList", chatList);
		
		return "common/sidebar/adminChat";
	}
	
	@GetMapping("sidebar/adminChatDetail")
	public String adminChatDetail(
			@RequestParam("ChattingRoomNo") int ChattingRoomNo,
			Model model,
			@SessionAttribute("loginMember") Member LoginMember
			) {
		
		List<ChattingMessage> messageList = service.adminChattingList(ChattingRoomNo);
		ChattingRoomDto chatroom = service.chatroom(ChattingRoomNo);
		model.addAttribute("loginMember",LoginMember);
		model.addAttribute("messageList",messageList);
		model.addAttribute("chatroom", chatroom);
		
		return "common/sidebar/adminChatDetail";
	}
	
	@ResponseBody
	@GetMapping("sidebar/createChatRoom")
	public int createChatRoom(
			@SessionAttribute("loginMember") Member loginMember
			) {

		int result = service.createChatRoom(loginMember.getMemberNo());

		return result;
	}
	
	@ResponseBody
	@GetMapping("sidebar/chattingMessageRead")
	public String chattingMessageRead(
			@RequestParam("chattingRoomNo") int chattingRoomNo,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		int result = service.chatRead(chattingRoomNo, loginMember.getMemberNo());
		
		return "";
	}
	
	@ResponseBody
	@GetMapping("sidebar/firstArCheck")
	public int firstArCheck(
			@RequestParam("memberNo") int memberNo
			) {
		
		int result = service.firstArCheck(memberNo);
		
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		
		return result;
	}
	
	
}
