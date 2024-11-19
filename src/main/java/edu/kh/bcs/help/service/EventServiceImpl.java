package edu.kh.bcs.help.service;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.common.util.FileUtil;
import edu.kh.bcs.help.dto.EventDto;
import edu.kh.bcs.help.mapper.EventMapper;
import io.lettuce.core.dynamic.annotation.Value;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:/config.properties")
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
		// ★★★★★★ 파일을 가져와서 이름변경을 하고
		// -> boardDto에 담아서 DB로 넘겨서 insert 하는것 ★★☆★☆★☆★☆
		// -> INSERT가 성공이 된다면???? 실제 파일을 서버에 올린다!

		String rename = null;

		// 1) 파일 업로드 확인
		if (!eventImage.isEmpty()) {

			// 2) 웹 접근경로(config.properties) + 변경된 파일명

			String originalRename = FileUtil.rename(eventImage.getOriginalFilename());
			String thumbRename = FileUtil.rename(eventImage.getOriginalFilename());

			String url = webPath + rename;

	        File originalImage = new File(webPath + originalRename);
	        File resizedImage = new File(webPath + thumbRename);

	        try {
	            // 이미지를 리사이즈하여 출력 경로에 저장
	            Thumbnails.of(originalImage)
	                .size(270, 200)  // 썸네일 크기 (너비 150px, 높이 150px)
	                .outputFormat("jpg")  // 출력 포맷 지정 (여기서는 jpg)
	                .toFile(resizedImage);

	            System.out.println("Thumbnail created successfully!");

	        } catch (IOException e) {
	            System.err.println("Error creating thumbnail: " + e.getMessage());
	            e.printStackTrace();
	        }
			
			inputEvent.setEventImage(url);

		}

		// 3) 게시글 부분 ( 제목, 내용, 작성자, 게시판 종류 ) INSERT
		int result = mapper.eventWrite(inputEvent);

		System.out.println(inputEvent.getEventNumber());

		// 삽입 실패 시
		if (result == 0)
			return 0;

		/* 삽입된 게시글 번호 */
		int boardNo = inputEvent.getEventNumber();

		// 4) DB UPDATE 수행

		// 파일 없을 시 업로드 하지 않고 리턴
		if (eventImage.isEmpty()) {
			return eventNumber;
		}

		try {
			// C:/uploadFiles/board2/ 폴더가 없으면 생성
			File folder = new File(folderPath);
			if (!folder.exists())
				folder.mkdir();

			// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
			eventImage.transferTo(new File(folderPath + rename));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventNumber;
	}
	
	
}