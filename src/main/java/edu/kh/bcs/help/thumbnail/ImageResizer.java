package edu.kh.bcs.help.thumbnail;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
	
public class ImageResizer {

    public static void main(String[] args) {
        // 리사이징할 원본 이미지 파일과 출력 파일 경로 지정
    	
    	//restaurantFolderPath + rename1
        File originalImage = new File("C:/BCS/event/image.jpg");
        File resizedImage = new File("C:/BCS/event/thumbnail.jpg");

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
    }
}