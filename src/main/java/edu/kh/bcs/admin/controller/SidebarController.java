package edu.kh.bcs.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SidebarController {

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
	
}
