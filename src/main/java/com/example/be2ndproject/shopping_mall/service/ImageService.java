package com.example.be2ndproject.shopping_mall.service;


import com.example.be2ndproject.shopping_mall.repository.Image.Image;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 이미지 업로드 및 저장을 담당하는 서비스 클래스
@Service
public class ImageService {

    // 이미지를 업로드하고 저장하는 메서드
    public List<Image> uploadImages(MultipartFile[] multipartFiles, Space space) throws IOException {
        List<Image> imageList = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            // 이미지 파일을 저장할 디렉토리 경로 설정
            String uploadDir = "images/";
            // 실제 이미지 파일 이름 생성
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 디렉토리 생성
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 이미지 파일을 저장할 경로 설정
            Path filePath = uploadPath.resolve(fileName);
            File dest = filePath.toFile();

            // 이미지 파일 저장
            file.transferTo(dest);

            // 이미지 엔티티 생성 및 설정
            Image image = new Image();
            image.setImageUrl(fileName); // 파일 이름을 URL로 설정 또는 실제 서버에서 접근 가능한 URL을 설정
            image.setSpace(space);

            imageList.add(image);
        }

        return imageList;
    }
}