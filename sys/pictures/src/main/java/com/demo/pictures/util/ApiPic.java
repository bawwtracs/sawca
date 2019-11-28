package com.demo.pictures.util;

import com.demo.pictures.entity.PictureRequestPic;
import com.demo.pictures.repository.PictureRequestPicRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Data
@Component
@ConfigurationProperties(prefix = "wozhitu.api")
public class ApiPic {

    private String host;
    private String keyFast;
    private String keyAccurate;
    private String search;
    private String add;

    @Resource
    private PictureRequestPicRepository requestPicRepository;

    public PictureRequestPic addOrSearch(String fileName, File file, boolean fast, String type) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpEntity<MultiValueMap<String, Object>> request = createRequest(file, fast);
            Long requestTime = new Date().getTime();
            String result = restTemplate.postForEntity(host + ("search".equals(type) ? search : add), request, String.class).getBody();
            Long responseTime = new Date().getTime();
            return record(fileName, file, requestTime, responseTime, result);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private HttpEntity<MultiValueMap<String, Object>> createRequest(File file, boolean fast) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        List<MediaType> accepts = new ArrayList<>();
        accepts.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accepts);
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("apikey", fast ? keyFast : keyAccurate);
        multiValueMap.add("photo", new FileSystemResource(file));
        multiValueMap.add("imgname", file.getName());
        return new HttpEntity<>(multiValueMap, headers);
    }

    private PictureRequestPic record(String fileName, File file, Long requestTime, Long responseTime, String result) {
        PictureRequestPic pictureRequestPic = new PictureRequestPic();
        pictureRequestPic.setName(fileName);
        pictureRequestPic.setRequestTime(requestTime);
        pictureRequestPic.setResponseTime(responseTime);
        pictureRequestPic.setResult(result);
        pictureRequestPic.setMark("path=" + file.getAbsolutePath());
        return requestPicRepository.save(pictureRequestPic);
    }
}
