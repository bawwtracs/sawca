package sawca.support.image.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sawca.support.image.entity.Request;
import sawca.support.image.entity.RequestImage;
import sawca.support.image.repository.RequestImageRepository;
import sawca.support.image.repository.RequestRepository;
import sawca.support.image.service.IRequestImageService;
import sawca.support.image.service.IRequestService;
import sawca.support.image.util.ImgServer;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
public class ImageRest {

    private static String addPath = "image/add";
    private static String searchPath = "image/search";

    public ImageRest() {
        try {
            Path addPhotoPath = Paths.get(addPath);
            if (!Files.exists(addPhotoPath)) {
                Files.createDirectories(addPhotoPath);
            }
            Path searchPhotoPath = Paths.get(searchPath);
            if (!Files.exists(searchPhotoPath)) {
                Files.createDirectories(searchPhotoPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private IRequestService requestService;
    @Resource
    private IRequestImageService requestImageService;
    @Resource
    private RequestRepository requestRepository;
    @Resource
    private RequestImageRepository requestImageRepository;
    @Resource
    private ImgServer imgServer;

    @PostMapping("/imgsvr/{operation}/{type}")
    public RequestImage addOrSearch(@RequestParam(value = "photo") MultipartFile image, @PathVariable String type, @PathVariable(value = "operation") String operation, @RequestParam(value = "requestId") Long requestId) {
        try {
            String imgName = image.getOriginalFilename();
            Path imgPath = Paths.get("add".equals(operation) ? addPath : searchPath).resolve(imgName);
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, imgPath, StandardCopyOption.REPLACE_EXISTING);
            return imgServer.addOrSearch(imgPath, type, operation, requestId, null, 0);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @PostMapping("/imgsvr/resend")
    public RequestImage reRequest(@RequestParam(value = "photo") MultipartFile image, @RequestParam Long requestImageId) {
        try {
            RequestImage requestImage = requestImageRepository.getOne(requestImageId);
            String imgName = image.getOriginalFilename();
            Path imgPath = Paths.get("add".equals(requestImage.getOperation()) ? addPath : searchPath).resolve(imgName);
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, imgPath, StandardCopyOption.REPLACE_EXISTING);
            return imgServer.addOrSearch(imgPath, requestImage.getType(), requestImage.getOperation(), requestImage.getRequestId(), requestImage.getId(), requestImage.getResend() + 1);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @GetMapping("/image/{folder}/{imgName}")
    public void getImage(HttpServletResponse response, @PathVariable("folder") String folder, @PathVariable(value = "imgName") String imgName) throws IOException {
        try {
            String suffix = imgName.substring(imgName.lastIndexOf(".") + 1);
            response.setContentType("image/" + suffix + ";charset=utf-8");
            response.setHeader("Content-Disposition", "inline; filename=" + imgName);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(Files.readAllBytes(Paths.get("add".equals(folder) ? addPath : searchPath).resolve(imgName)));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.error("get Image Error");
        }
    }

    @GetMapping("/del/image")
    public String delImage(@RequestParam("imgName") String imgName, @RequestParam("type") String type) {
        return imgServer.delImage(imgName, type);
    }

    @GetMapping("/request/statistics")
    public String statisticsRequestImage() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("addCount", requestImageService.addCount());
            jsonObject.put("fastAddCount", requestImageService.fastAddCount());
            jsonObject.put("accurateAddCount", requestImageService.accurateAddCount());
            jsonObject.put("latestFastAddCount", requestImageService.latestFastAddCount());
            jsonObject.put("latestAccurateAddCount", requestImageService.latestAccurateAddCount());
            jsonObject.put("latestFastAddAvgTime", requestImageService.latestFastAddAvgTime());
            jsonObject.put("latestFastSearchAvgTime", requestImageService.latestFastSearchAvgTime());
            jsonObject.put("latestAccurateAddAvgTime", requestImageService.latestAccurateAddAvgTime());
            jsonObject.put("latestAccurateSearchAvgTime", requestImageService.latestAccurateSearchAvgTime());
            jsonObject.put("fastAddAvgTime", requestImageService.fastAddAvgTime());
            jsonObject.put("accurateAddAvgTime", requestImageService.accurateAddAvgTime());
            jsonObject.put("fastSearchAvgTime", requestImageService.fastSearchAvgTime());
            jsonObject.put("accurateSearchAvgTime", requestImageService.accurateSearchAvgTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @PostMapping("/request")
    public Request createRequest(@RequestBody Request request) {
        return requestRepository.save(request);
    }

    @GetMapping("/requests")
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @GetMapping("/request/{id}")
    public Request getRequestById(@PathVariable("id") Long id) {
        return requestRepository.getOne(id);
    }

    @PutMapping("/request/{id}")
    public Request updateRequestById(@PathVariable("id") Long id, @RequestBody Request
            request) {
        request.setId(id);
        return requestRepository.save(request);
    }

    @PostMapping("/request/image")
    public RequestImage createRequestImage(@RequestBody RequestImage requestimage) {
        return requestImageRepository.save(requestimage);
    }

    @GetMapping("/request/image")
    public List<RequestImage> getAllRequestImages() {
        return requestImageRepository.findAll();
    }

    @GetMapping("/request/image/{id}")
    public RequestImage getRequestImageById(@PathVariable("id") Long id) {
        return requestImageRepository.getOne(id);
    }

    @PutMapping("/request/image/{id}")
    public RequestImage updateRequestImageById(@PathVariable("id") Long id, @RequestBody RequestImage requestimage) {
        requestimage.setId(id);
        return requestImageRepository.save(requestimage);
    }
}
