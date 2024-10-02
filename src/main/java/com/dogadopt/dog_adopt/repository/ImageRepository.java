package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i " +
           "FROM Image i " +
           "WHERE i.isFirstPicture = true")
    List<Image> getFirstImageOfDogs();
}
