package edu.kh.bcs.review.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.bcs.common.util.FileUtil;
import edu.kh.bcs.review.dto.Review;
import edu.kh.bcs.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PropertySource("classpath:/config.properties")
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReviewServiewImpl implements ReviewService {
	
	@Value("${my.review.web-path}") 
	private String reviewWebPath; 
	
	@Value("${my.review.folder-path}") 
	private String reviewFolderPath; 
	
	
	
	private final ReviewMapper mapper;
	
	// 리뷰 정보 insert
	@Override
	public int reviewInsert(Review review, MultipartFile imgInput) {
		
		// 파일 업로드 확인
		if (imgInput.isEmpty()) {
			System.out.println("파일 업로드 오류");
			return 0;
		}
			
		String rename = FileUtil.rename(imgInput.getOriginalFilename());

		// 웹 접근 경로(config.properties) + 변경된 파일명 준비
		String url = reviewWebPath + rename;
		
		review.setReviewImg(url);
		
		// DB UPDATE
		int result = mapper.reviewInsert(review);
		
		
		// 로컬 저장소 업로드
		try {
			// C:/BCS/review/  폴더가 없으면 생성
			File folder = new File(reviewFolderPath);
			if(!folder.exists()) folder.mkdirs();
			
			// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
			imgInput.transferTo(new File(reviewFolderPath + rename));

		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("이미지 입력 실패");
		}
		
		
		return result;
	}

	
	
	// 수정 페이지 리뷰 정보 불러오는 메서드
	@Override
	public Review selectReview(int reviewNo) {
		return mapper.selectReview(reviewNo);
	}
	
	
	@Override
	public int reviewUpdate(Review review, MultipartFile imgInput) {
		
		int result = 0;
		
		// 이미지 파일 업로드가 된 경우
		if (!imgInput.isEmpty()) {
			
			String rename = FileUtil.rename(imgInput.getOriginalFilename());

			// 웹 접근 경로(config.properties) + 변경된 파일명 준비
			String url = reviewWebPath + rename;
			
			review.setReviewImg(url);
			
			// DB UPDATE
			result = mapper.reviewUpdate(review);
			
			// 로컬 저장소 업로드
			try {
				// C:/BCS/review/  폴더가 없으면 생성
				File folder = new File(reviewFolderPath);
				if(!folder.exists()) folder.mkdirs();
				
				// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
				imgInput.transferTo(new File(reviewFolderPath + rename));

			} catch (Exception e) {
				e.printStackTrace();
				throw new Error("이미지 입력 실패");
			}
		}
		
		// 이미지 파일 올리지 않은 경우
		else {
			result = mapper.reviewUpdate(review);
		}
		
		return result;
	}
}
