package edu.kh.bcs.map.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.bcs.map.mapper.mapMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class mapServiceImpl implements mapService{
	
	
	private final mapMapper mapper;
	
	
	

}
