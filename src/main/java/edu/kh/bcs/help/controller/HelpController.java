package edu.kh.bcs.help.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.bcs.main.MainController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HelpController {

	/* 고객센터 페이지로 전환 */
	@GetMapping("help")
	public String help() {
		return "help";
	}
	
	
	
}
