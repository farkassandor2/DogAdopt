package com.dogadopt.dog_adopt.service.image;

import com.dogadopt.dog_adopt.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public void uploadFile(List<MultipartFile> images) {

    }
}
