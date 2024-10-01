package com.dogadopt.dog_adopt.service.image;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {


    void uploadFile(List<MultipartFile> images);
}
