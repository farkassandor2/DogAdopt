package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i " +
           "FROM Image i " +
           "WHERE i.imageType = ?1 " +
           "AND i.isFirstPicture = true")
    List<Image> getFirstImage(ImageType imageType);
}
