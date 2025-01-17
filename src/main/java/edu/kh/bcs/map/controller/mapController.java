package edu.kh.bcs.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.bcs.map.service.mapService;
import lombok.RequiredArgsConstructor;



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
