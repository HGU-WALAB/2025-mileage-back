package com.csee.swplus.mileage.auth.service;

import lombok.extern.slf4j.Slf4j;
import com.csee.swplus.mileage.auth.dto.AuthDto;
import com.csee.swplus.mileage.auth.exception.FailedHisnetLoginException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class HisnetLoginService {

    @Value("${hisnet.access-key}")
    private String accessKey;

    public AuthDto callHisnetLoginApi(AuthDto dto) {
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("token", dto.getHisnetToken());
        requestBody.put("accessKey", accessKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://walab.info:8443/HisnetLogin/api/hisnet/login/validate";
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        log.info("Sending request to Hisnet API: URL={}, token={}", url, dto.getHisnetToken()); // 요청 로그 추가

        try {
            ParameterizedTypeReference<Map<String, Object>> typeRef =
                    new ParameterizedTypeReference<Map<String, Object>>(){};
            ResponseEntity<Map<String, Object>> resultMap =
                    restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, typeRef);
            Map<String, Object> result = resultMap.getBody();
            assert result != null;

            log.info("Received response from Hisnet API: {}", result); // 응답 로그 추가

            AuthDto authDto = AuthDto.builder()
                    .studentId(result.get("uniqueId").toString())
                    .studentName(result.get("name").toString())
                    .studentEmail(result.get("email").toString())
                    .department(result.get("department").toString())
                    .major1(result.get("major1").toString())
                    .major2(result.get("major2").toString())
                    .grade(Integer.parseInt(result.get("grade").toString()))
                    .term(Integer.parseInt(result.get("semester").toString()))
                    .build();

            log.info("AuthDto created: {}", authDto);  // 생성된 AuthDto 객체를 로그로 출력

            return authDto;
        } catch (HttpStatusCodeException e) {
            log.error("Error response from Hisnet API: StatusCode={}, Body={}",
                    e.getStatusCode().value(), e.getResponseBodyAsString()); // 에러 로그 추가

            Map<String, Object> result = new HashMap<>();
            try {
                result = new ObjectMapper().readValue(e.getResponseBodyAsString(), new TypeReference<Map<String, Object>>() {});
            } catch (Exception ex) {
                throw new FailedHisnetLoginException("예상치 못한 변수 발생", 500);
            }
            throw new FailedHisnetLoginException(
                    result.get("message").toString(), e.getStatusCode().value());
        }
    }
}
