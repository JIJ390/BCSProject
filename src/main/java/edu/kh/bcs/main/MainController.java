package edu.kh.bcs.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	
	@RequestMapping("/")
	public String main() {
		
		
		
		log.debug("aaaaa");
		
		
		
		return "main/main";
	}

	
}
