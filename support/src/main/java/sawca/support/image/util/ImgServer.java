package sawca.support.image.util;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sawca.support.image.entity.RequestImage;
import sawca.support.image.repository.RequestImageRepository;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Data
@Component
@ConfigurationProperties(prefix = "wozhitu.api")
public class ImgServer {

    private String host;
    private String keyFast;
    private String keyAccurate;
    private String searchFast;
    private String searchAccurate;
    private String addFast;
    private String addAccurate;
    private String deleteFast;
    private String deleteAccurate;

    @Resource
    private RequestImageRepository requestImageRepository;

    public RequestImage addOrSearch(Path imgPath, String type, String operation, Long requestId, Long requestImageId, Integer resend) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String apiKey = "accurate".equals(type) ? keyAccurate : keyFast;
            String url;
            if ("add".equals(operation)) {
                url = "accurate".equals(type) ? addAccurate : addFast;
            } else {
                url = "accurate".equals(type) ? searchAccurate : searchFast;
            }
            url = host + url;
            File image = imgPath.toFile();
            HttpEntity<MultiValueMap<String, Object>> request = createRequest(image, apiKey);
            Long requestTime = new Date().getTime();
            String result = restTemplate.postForEntity(url, request, String.class).getBody();
            Long responseTime = new Date().getTime();
            return saveRequestImage(image, requestTime, responseTime, result, operation, requestId, type, requestImageId, resend);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new RequestImage();
    }

    public String delImage(String imgName, String type) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String apiKey = "accurate".equals(type) ? keyAccurate : keyFast;
            String url = "accurate".equals(type) ? deleteAccurate : deleteFast;
            url = host + url;
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("apikey", apiKey);
            multiValueMap.add("imgname", imgName);
            return restTemplate.postForEntity(url, new HttpEntity<>(multiValueMap, headers), String.class).getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private HttpEntity<MultiValueMap<String, Object>> createRequest(File file, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        List<MediaType> accepts = new ArrayList<>();
        accepts.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accepts);
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("apikey", apiKey);
        multiValueMap.add("photo", new FileSystemResource(file));
        multiValueMap.add("imgname", absName(file.getName()));
        multiValueMap.add("imgurl", file.getPath());
        multiValueMap.add("p", 1);
        multiValueMap.add("numperpage", 1000);
        multiValueMap.add("maxitems", 1000);
        return new HttpEntity<>(multiValueMap, headers);
    }

    private RequestImage saveRequestImage(File file, Long requestTime, Long responseTime, String result, String operation, Long requestId, String type, Long requestImageId, Integer resend) {
        RequestImage requestImage = new RequestImage();
        if (requestImageId != null) {
            requestImage.setId(requestImageId);
        }
        requestImage.setName(file.getName());
        requestImage.setPath(file.getPath());
        requestImage.setRequestTime(requestTime);
        requestImage.setResponseTime(responseTime);
        requestImage.setOperation(operation);
        requestImage.setResult(result);
        requestImage.setRequestId(requestId);
        requestImage.setType(type);
        requestImage.setResend(resend);
        return requestImageRepository.save(requestImage);
    }

    /**
     * 1.接口文档要求：图片的名字. 必需唯一. 请使用由字母, 数字或-及_组成的图片名. 不要使用中文图片名.
     * 2.文件名重复采用覆盖模式，图片服务器不应多次索引同名文件（搜索库无限制增大、垃圾索引增加、清理麻烦、搜索结果包含垃圾）
     * 3.根据#1,#2，用时间戳之类的方式应排除，所以必须对同名文件产生唯一值，要么存库，要么摘要。
     * 4.采用文件名md5摘要，摘要之后，可以生成唯一值给图片索引服务器。
     *
     * @param fileName
     * @return
     */
    private String absName(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return DigestUtils.md5DigestAsHex(fileName.getBytes(StandardCharsets.UTF_8)) + "." + suffix;
    }
}
