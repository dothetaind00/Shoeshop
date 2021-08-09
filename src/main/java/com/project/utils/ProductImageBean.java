package com.project.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductImageBean {

    private MultipartFile image1;

    private MultipartFile image2;

    private MultipartFile image3;

    private MultipartFile image4;

}
