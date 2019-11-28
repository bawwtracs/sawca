package com.demo.pictures.repository;

import com.demo.pictures.entity.PictureRequestPic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRequestPicRepository extends JpaRepository<PictureRequestPic, Long> {
}
