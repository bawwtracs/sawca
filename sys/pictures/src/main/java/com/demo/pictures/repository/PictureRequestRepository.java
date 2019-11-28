package com.demo.pictures.repository;

import com.demo.pictures.entity.PictureRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRequestRepository extends JpaRepository<PictureRequest, Long> {
}
