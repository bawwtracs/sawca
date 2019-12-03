package com.demo.pictures.repository;

import com.demo.pictures.entity.RequestImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestImageRepository extends JpaRepository<RequestImage, Long> {
}
