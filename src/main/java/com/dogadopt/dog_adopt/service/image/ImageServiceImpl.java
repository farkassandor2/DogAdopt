package com.dogadopt.dog_adopt.service.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.exception.CloudinaryException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final Cloudinary cloudinary;

    @Override
    public List<Image> uploadFile(List<MultipartFile> imageFiles) {
        List<Image> images = new ArrayList<>();
        AtomicReference<Map> uploadResult = new AtomicReference<>();

        if (imageFiles == null) {
            return images;
        }
        imageFiles.forEach(file -> {
            if (file != null && !file.isEmpty()) {
                try {
                    uploadResult.set(cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()));
                } catch (IOException e) {
                    throw new CloudinaryException("Error uploading file");
                }
                String imageUrl = (String) uploadResult.get().get("secure_url");
                images.add(new Image(imageUrl, file.getOriginalFilename()));
            }
        });

        return images;
    }
}
