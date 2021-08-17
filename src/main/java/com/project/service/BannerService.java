package com.project.service;

import com.project.entity.Banner;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

public interface BannerService {

    Banner getById(Integer id);

    void deleteAll();

    void delete(Banner entity);

    void deleteById(Integer id);

    long count();

    boolean existsById(Integer id);

    Optional<Banner> findById(Integer id);

    List<Banner> findAll();

    Banner save(Banner entity);

    Optional<Banner> findByTitle(String title);

    String saveImageUrl(MultipartFile file);
}
