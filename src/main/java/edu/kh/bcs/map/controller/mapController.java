package edu.kh.bcs.map.controller;

import org.springframework.stereotype.Controller;

import edu.kh.bcs.map.service.mapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequiredArgsConstructor
@RequestMapping("bcsMap")
public class mapController {

	private final mapService service;
	
	
	
	@RequestMapping("/map")
	public String map(
) {
		
		
		return "bcsMap/map";
	}
	
	
	
	
	
	
	
}
