package com.dogadopt.dog_adopt.service.image;


import com.dogadopt.dog_adopt.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {


    List<Image> uploadFile(List<MultipartFile> images);
}
