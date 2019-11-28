package com.demo.pictures.rest;

import com.demo.pictures.entity.PictureRequest;
import com.demo.pictures.entity.PictureRequestPic;
import com.demo.pictures.repository.PictureRequestPicRepository;
import com.demo.pictures.repository.PictureRequestRepository;
import com.demo.pictures.service.IPictureRequestPicService;
import com.demo.pictures.service.IPictureRequestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
