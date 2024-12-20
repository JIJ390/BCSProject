package edu.kh.bcs.help.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.common.util.FileUtil;
import edu.kh.bcs.help.dto.EventDto;
import edu.kh.bcs.help.dto.EventPagination;
import edu.kh.bcs.help.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:/config.properties")
@Slf4j
public class EventServiceImpl implements EventService {
	
	private final EventMapper mapper;
	
	@Value("${my.event.web-path}")
	private String webPath;

	@Value("${my.event.folder-path}")
	private String folderPath;

	// 게시글 등록
	@Override
	public int eventWrite(EventDto inputEvent, MultipartFile eventImage) {

		// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		// ★★★★★★ 파일을 가져와서 이름변경 하고
		// -> Dto 에 담아서 DB로 넘겨서 insert 하는것 ★★☆★☆★☆★☆
		// -> INSERT가 성공이 된다면???? 실제 파일의 경로만 DB 에 저장

		int eventNo = 0;

		// 1) 파일 업로드 확인
		if (!eventImage.isEmpty()) {

			// 2) 웹 접근경로(config.properties) + 변경된 파일명

			String originalRename = FileUtil.rename(eventImage.getOriginalFilename());
			String thumbRename = FileUtil.rename(eventImage.getOriginalFilename());

			
			// DB 에 저장되는 경로
			String url1 = webPath + originalRename;
			String url2 = webPath + thumbRename;
			
	
	        try {
	        	
//		        File originalImage = new File(folderPath + eventImage.getOriginalFilename());
		        File originalImage = new File(folderPath + originalRename);
		        originalImage.createNewFile();
		        
		        //  MultipartFile을 file 로 변환하기
			    FileOutputStream fos = new FileOutputStream(originalImage);
			    fos.write(eventImage.getBytes());
			    fos.close();
		        
		        
		        File resizedImage = new File(folderPath + thumbRename);
		        
		        
		    	// 원본 파일명 뒤에서 부터 검색해서 처음 찾은 "."의 index
				int index = eventImage.getOriginalFilename().lastIndexOf(".") + 1;
				
				// 원본 파일명 "." 부터 끝까지 잘라낸 문자열 == .확장자
				String ext = eventImage.getOriginalFilename().substring(index);
		        
		        
	            // 이미지를 리사이즈하여 출력 경로에 저장
	            Thumbnails.of(originalImage)
	                .size(270, 200)  // 썸네일 크기 (너비 150px, 높이 150px)
	                .outputFormat(ext)
	                .toFile(resizedImage);
	            

	            System.out.println("Thumbnail created successfully!");

	        } catch (IOException e) {
	            System.err.println("Error creating thumbnail: " + e.getMessage());
	            e.printStackTrace();
	        }
			
			inputEvent.setEventImage(url1);
			inputEvent.setEventThumbnail(url2);

			
			
			// 3) 게시글 부분 ( 제목, 내용, 이미지 ) INSERT
			int result = mapper.eventWrite(inputEvent);

			System.out.println(inputEvent.getEventNumber());

			// 삽입 실패 시
			if (result == 0)
				return 0;

			/* 삽입된 게시글 번호 */
			eventNo = inputEvent.getEventNumber();

			// 4) DB UPDATE 수행

			// 파일 없을 시 업로드 하지 않고 리턴
			if (eventImage.isEmpty()) {
				return eventNo;
			}

//			try {
//				// C에 폴더가 없으면 생성
//				File folder = new File(folderPath);
//				if (!folder.exists())
//					folder.mkdirs();
//
//			
//				
//				// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
//				eventImage.transferTo(new File(folderPath + originalRename));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
			
			
		}


		return eventNo;
	}
	
	@Override
	public Map<String, Object> selectEventList(int cp) {
		
		int listCount = mapper.getListCount();
		
		EventPagination pagination = new EventPagination(cp, listCount);
		int limit = pagination.getLimit();
		int offset = (cp - 1)*limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<EventDto> eventList = mapper.selectEventList(rowBounds);
		
		Map<String, Object> map = 
				Map.of("eventList", eventList, "pagination", pagination);
		
		
		return map;
		}
	
	
	
	@Override
	public EventDto detailViewEvent(int eventNo) {
		return mapper.detailViewEvent(eventNo);
	}
	
	
} // end