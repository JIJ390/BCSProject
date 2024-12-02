package edu.kh.bcs.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.admin.service.AdminService;
import edu.kh.bcs.chatting.dto.ChattingMessage;
import edu.kh.bcs.chatting.dto.ChattingRoomDto;
import edu.kh.bcs.device.dto.Order;
import edu.kh.bcs.device.dto.reviewRNDto;
import edu.kh.bcs.myPage.dto.Member;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class SidebarController {
	
	@Autowired
	private AdminService service;

	@RequestMapping("sidebarHome")
	public String getHomeContent(
			Model model,
			@SessionAttribute(value = "loginMember", required=false) Member loginMember
			) {
		
		if(loginMember == null) {
			return "common/sidebar/home";
			
		}
		int memberNo = loginMember.getMemberNo();
		List<reviewRNDto> orderList = service.getOrderList(memberNo);
		
		model.addAttribute("orderList",orderList);
		
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
	
	// 채팅방 생성
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
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("sidebar/reviewNoti")
	public int reviewNoti(
			@RequestBody Map<String, String> map
			) {
		
		String orderNo = map.get("orderNo");
		String memberNo = map.get("memberNo");
		
		int result = service.insertReviewNoti(orderNo, memberNo);
		
		System.out.println(orderNo);
		System.out.println(memberNo);
		System.out.println(orderNo);
		System.out.println(memberNo);
		System.out.println(orderNo);
		System.out.println(memberNo);
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("sidebar/chattingImg")
	public String chattingImg(
			@RequestParam("img") MultipartFile img
			) {
		
		String path = service.uploadImg(img);
		
		return path;
	}
	
	
	@ResponseBody
	@DeleteMapping("sidebar/deleteReviewRN")
	public int deleteReviewRN(
			@RequestBody int orderNo
			) {
		
		System.out.println(orderNo);
		
		int result = service.deteleReviewRN(orderNo);
		
		return result;
	}
	
	
	
}
