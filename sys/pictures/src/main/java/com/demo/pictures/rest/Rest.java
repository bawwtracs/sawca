package com.demo.pictures.rest;

import com.demo.pictures.entity.Request;
import com.demo.pictures.entity.RequestImage;
import com.demo.pictures.repository.RequestImageRepository;
import com.demo.pictures.repository.RequestRepository;
import com.demo.pictures.service.IRequestImageService;
import com.demo.pictures.service.IRequestService;
import com.demo.pictures.util.ImgServer;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api")
public class Rest {

    private static String addPath = "image/add";
    private static String searchPath = "image/search";

    public Rest() {
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
    public RequestImage add(@RequestParam(value = "photo") MultipartFile image, @PathVariable String type, @PathVariable(value = "operation") String operation, @RequestParam(value = "requestId") Long requestId) {
        try {
            String imgName = image.getOriginalFilename();
            Path imgPath = Paths.get("add".equals(operation) ? addPath : searchPath).resolve(imgName);
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, imgPath, StandardCopyOption.REPLACE_EXISTING);
            return imgServer.addOrSearch(imgPath, type, operation, requestId);
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
    public List<Map<String, Object>> statisticsRequestImage() {
        return requestImageService.statisticsRequestImage();
    }

    @GetMapping("/count/img")
    public Long getRequestById() {
        return requestImageService.countRequestImage();
    }

    @PostMapping("/request")
    public Request createRequest(@RequestBody Request request) {
        if (request.getRequestTime() == null || request.getRequestTime() == 0) {
            request.setRequestTime(new Date().getTime());
        }
        return requestRepository.save(request);
    }

    @GetMapping("/request")
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
