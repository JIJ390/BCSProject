package edu.kh.bcs.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration // 서버 실행 시 해당 클래스 내부 메서드를 모두 실행
							 // (메서드에 서버 설정 내용 작성)
@PropertySource("classpath:/config.properties")
// -> config.propertiew 에 작성된 내용을 얻어와서 사용하곘다!!
public class FileConfig implements WebMvcConfigurer{
	
	// config.properties 에서 ${key} 가 일치하는 값을 얻어와 변수에 대입
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeThreshold;	// 임계값
	
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize; // 요청당 파일 최대 크기
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize; // 개별 파일당 최대 크기
	
	@Value("${spring.servlet.multipart.location}")
	private String location; // 임계값 초과 시 임시 저장 폴더 경로
	
	//-----------------------------------------
	
	// test 관련 값
	@Value("${my.test.resource-handler}")
	private String testResourceHandler;			// 테스트 이미지 요청 주소
	
	@Value("${my.test.resource-location}")
	private String testResourceLocation;		// 테스트 이미지 요청 시 
																					// 연결될 서버 폴더 경로
	@Value("${my.device.resource-handler}")
	private String deviceResourceHandler;
	
	@Value("${my.device.resource-location}")
	private String deviceResourceLocation;
	// 디바이스 컬러
	@Value("${my.deviceColor.resource-handler}")
	private String deviceColorResourceHandler;
	
	@Value("${my.deviceColor.resource-location}")
	private String deviceColorResourceLocation;
	
	//---------------------------------------------------------------
	// 파일 연결 정보 쓰는 곳
	
	// 이벤트 게시판용
	@Value("${my.event.resource-handler}")
	private String eventResourceHandler;			// 테스트 이미지 요청 주소
	
	@Value("${my.event.resource-location}")
	private String eventResourceLocation;		// 테스트 이미지 요청 시 
	
	
	//---------------------------------------------------------------------------
	
	// 리뷰 
	@Value("${my.review.resource-handler}")
	private String reviewResourceHandler;			//
	
	@Value("${my.review.resource-location}")
	private String reviewResourceLocation;		// 
	

	/* MultipartResolver 설정 */
	@Bean
	public MultipartConfigElement configElement() {
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeThreshold));
		
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));		// 개별 파일 최대 크기
		
		factory.setMaxRequestSize(DataSize.ofBytes(maxRequestSize));	// 요청 파일 최대 크기
		
		factory.setLocation(location);
		
		
		return factory.createMultipartConfig();
	}
	
	
	// MultipartResolver 객체를 bean으로 추가
	// -> 추가 후 위에서 만든 MultipartConfig를 자동으로 이용
	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver multipartResolver
			= new StandardServletMultipartResolver();
		
		return multipartResolver;
	}
	

	// 인터넷(웹) 으로 특정 형태의 요청(js, css, image)이 있을 경우
	// 서버 컴퓨터의 특정 폴더와 연결하는 설정을 작성하는 메서드
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler(testResourceHandler)			// /images/test/**
		.addResourceLocations(testResourceLocation);  // file:///C:/bcs/test/	
		
		registry
		.addResourceHandler(deviceResourceHandler)			// /images/device/**
		.addResourceLocations(deviceResourceLocation);  // file:///C:/bcs/device/	
		
		registry
		.addResourceHandler(deviceColorResourceHandler)			// /images/device/**
		.addResourceLocations(deviceColorResourceLocation);  // file:///C:/bcs/deviceColor/	
		
		
		
		
		registry
		.addResourceHandler(eventResourceHandler)					// /images/review/**
		.addResourceLocations(eventResourceLocation);			// file:///C:/uploadFiles/review/
		
		registry
		.addResourceHandler(reviewResourceHandler)					
		.addResourceLocations(reviewResourceLocation);			
		
		registry
		.addResourceHandler(deviceResourceHandler)			// /images/test/**
		.addResourceLocations(deviceResourceLocation);  // file:///C:/uploadFiles/test/	
		
	}
	
	
	

}
