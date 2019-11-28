package com.demo.pictures.rest;

import com.demo.pictures.entity.PictureRequest;
import com.demo.pictures.entity.PictureRequestPic;
import com.demo.pictures.repository.PictureRequestPicRepository;
import com.demo.pictures.repository.PictureRequestRepository;
import com.demo.pictures.service.IPictureRequestPicService;
import com.demo.pictures.service.IPictureRequestService;
import com.demo.pictures.util.ApiPic;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/picture")
public class Rest {

    @Resource
    private IPictureRequestService requestService;
    @Resource
    private IPictureRequestPicService requestPicService;
    @Resource
    private PictureRequestRepository requestRepository;
    @Resource
    private PictureRequestPicRepository requestPicRepository;
    @Resource
    private ApiPic apiPic;

    @PostMapping("/add")
    public PictureRequestPic add(@RequestParam(value = "photo") MultipartFile multipartFile, @RequestParam(value = "fast") boolean fast) {
        if (!multipartFile.isEmpty()) {
            try {
                String fileName = multipartFile.getOriginalFilename();
                String prefix = fileName.substring(fileName.lastIndexOf("."));
                File file;
                file = File.createTempFile(UUID.randomUUID().toString().replaceAll("-", ""), prefix);
                multipartFile.transferTo(file);
                PictureRequestPic pictureRequestPic = apiPic.addOrSearch(fileName, file, fast, "add");
                file.delete();
                return pictureRequestPic;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.info("uploaded file was empty");
            return null;
        }
        return null;
    }

    @PostMapping("/search")
    public PictureRequestPic search(@RequestParam(value = "photo") MultipartFile multipartFile, @RequestParam(value = "fast") boolean fast) {
        if (!multipartFile.isEmpty()) {
            try {
                String fileName = multipartFile.getOriginalFilename();
                String prefix = fileName.substring(fileName.lastIndexOf("."));
                File file;
                file = File.createTempFile(UUID.randomUUID().toString().replaceAll("-", ""), prefix);
                multipartFile.transferTo(file);
                PictureRequestPic pictureRequestPic = apiPic.addOrSearch(fileName, file, fast, "search");
                file.delete();
                return pictureRequestPic;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.info("uploaded file was empty");
            return null;
        }
        return null;
    }


    @PostMapping("/request")
    public PictureRequest createPictureRequest(@RequestBody PictureRequest pictureRequest) {
        return requestRepository.save(pictureRequest);
    }

    @GetMapping("/request")
    public List<PictureRequest> getAllPictureRequests() {
        return requestRepository.findAll();
    }

    @GetMapping("/request/{id}")
    public PictureRequest getPictureRequestById(@PathVariable("id") Long id) {
        return requestRepository.getOne(id);
    }

    @PutMapping("/request/{id}")
    public PictureRequest updatePictureRequestById(@PathVariable("id") Long id, @RequestBody PictureRequest pictureRequest) {
        pictureRequest.setId(id);
        return requestRepository.save(pictureRequest);
    }

    @PostMapping("/requestPic")
    public PictureRequestPic createPictureRequestPic(@RequestBody PictureRequestPic pictureRequestPic) {
        return requestPicRepository.save(pictureRequestPic);
    }

    @GetMapping("/requestPic")
    public List<PictureRequestPic> getAllPictureRequestPics() {
        return requestPicRepository.findAll();
    }

    @GetMapping("/requestPic/{id}")
    public PictureRequestPic getPictureRequestPicById(@PathVariable("id") Long id) {
        return requestPicRepository.getOne(id);
    }

    @PutMapping("/requestPic/{id}")
    public PictureRequestPic updatePictureRequestPicById(@PathVariable("id") Long id, @RequestBody PictureRequestPic pictureRequestPic) {
        pictureRequestPic.setId(id);
        return requestPicRepository.save(pictureRequestPic);
    }
}
