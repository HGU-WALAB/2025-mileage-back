package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.file.FileService;
import com.csee.swplus.mileage.profile.domain.Info;
import com.csee.swplus.mileage.profile.dto.InfoRequestDto;
import com.csee.swplus.mileage.profile.dto.InfoResponseDto;
import com.csee.swplus.mileage.profile.dto.MessageResponse;
import com.csee.swplus.mileage.profile.mapper.InfoMapper;
import com.csee.swplus.mileage.profile.repository.ProfileInfoRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileInfoService {
    private final InfoMapper infoMapper;
    private final ProfileInfoRepository profileInfoRepository;
    private final FileService fileService;

    @Value("${file.profile-upload-dir}")
    @Getter
    private String uploadDir;

    public InfoResponseDto getInfo(String studentId) {
        infoMapper.insertIfNotExists(studentId);

        InfoResponseDto res = infoMapper.findInfoByUserId(studentId);
        return new InfoResponseDto(
                res.getStudentId(),
                res.getStudentName(),
                res.getProfile_image_url(),
                res.getSelf_description(),
                res.getJob(),
                res.getGithub_link(),
                res.getInstagram_link(),
                res.getBlog_link(),
                res.getLinkedin_link()
        );
    }

    @Transactional
    public MessageResponse patchInfo(
            String studentId,
            String self_description,
            String job,
            String github_link,
            String instagram_link,
            String blog_link,
            String linkedin_link,
            MultipartFile file
    ) {
        try {
            Info info = profileInfoRepository.findBySnum(studentId).orElseThrow(() -> new RuntimeException("Not found"));

            if(!info.getSnum().equals(studentId)) {
                throw new RuntimeException("해당 프로필에 대한 수정 권한이 없습니다.");
            }
            if (self_description != null) {
                info.setSelfDescription(self_description);
            }
            if (job != null ) {
                info.setJob(job);
            }
            if (github_link != null) {
                info.setGithubLink(github_link);
            }
            if (instagram_link != null) {
                info.setInstagramLink(instagram_link);
            }
            if (blog_link != null) {
                info.setBlogLink(blog_link);
            }
            if (linkedin_link != null) {
                info.setLinkedinLink(linkedin_link);
            }
            if (file != null && !file.isEmpty()) {
                if (info.getProfileImageUrl() != null && !info.getProfileImageUrl().isEmpty()) {
                    fileService.deleteFile(info.getProfileImageUrl(), uploadDir);
                }

                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = UUID.randomUUID().toString() + extension;
                String savedFilename = fileService.saveFile(file, uploadDir, uniqueFilename);
                log.info("savedFilename: {}", savedFilename);

                info.setProfileImageUrl(savedFilename);
            }
            profileInfoRepository.save(info);
            return new MessageResponse("프로필이 수정되었습니다.");
        } catch (IOException e) {
            log.error("⚠️ 프로젝트 파일 저장 중 오류 발생: ", e);
            throw new RuntimeException("프로젝트 수정 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.error("⚠️ 프로젝트 수정 중 오류 발생: ", e);
            throw new RuntimeException("프로젝트 수정 중 오류가 발생했습니다.");
        }
    }
}
