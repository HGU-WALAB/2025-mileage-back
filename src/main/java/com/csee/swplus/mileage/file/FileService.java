package com.csee.swplus.mileage.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption; // 파일 복사 관련 설정 제공 e.g. REPLACE_EXISTING
//import java.nio.file.attribute.PosixFilePermission;
//import java.nio.file.attribute.PosixFilePermissions;
//import java.util.Set;


@Service
@Slf4j
public class FileService {
    public String saveFile(MultipartFile file, String uploadDir, String filename) throws IOException {
//        1. 업로드 디렉토리 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
//            mkdir 와의 차이: mkdirs 는 만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우 상위 디렉토리까지 생성
            directory.mkdirs();
        }

//        2. 파일 저장
//        uploadDir 경로를 Path 객체로 변환
//        새 파일명과 결합하여 최종 저장 위치를 설정
        Path targetLocation = Paths.get(uploadDir).resolve(filename);

//        file.getInputStream() 을 사용하여 업로드된 파일의 데이터를 읽음
//        targetLocation 경로에 복사
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetLocation);
        }

//        3. 저장된 파일명 (새로 만들어진 고유의 파일명) 반환
        return filename;
    }

    public void deleteFile(String filename, String uploadDir) throws IOException {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("파일 삭제 중 오류 발생: " + filename, e);
            throw new RuntimeException("파일 삭제 중 오류가 발생했습니다.");
        }
    }

    public String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
        }
    }
}
