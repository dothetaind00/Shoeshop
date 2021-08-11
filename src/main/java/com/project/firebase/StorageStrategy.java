package com.project.firebase;

import org.springframework.web.multipart.MultipartFile;

public interface StorageStrategy {

    String saveImage(MultipartFile file, String path);
    

}
