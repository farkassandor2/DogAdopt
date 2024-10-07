package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i " +            //Ha nem lesz használva akkor törölni
           "FROM Image i " +
           "WHERE i.imageType = ?1 " +
           "AND i.isFirstPicture = true")
    List<Image> getFirstImage(ImageType imageType);

    @Query("SELECT i " +
           "FROM Image i " +
           "WHERE i.shelter = ?1")
    Image getImageForShelter(Shelter shelter);

    @Query("SELECT i " +
           "FROM Image i " +
           "WHERE i.dog = ?1 " +
           "AND i.isFirstPicture = true ")
    Image getFirstImageOfDog(Dog actualDog);

    @Query("SELECT i.url " +
           "FROM Image i " +
           "WHERE i.dog = ?1")
    List<String> getAllImagesForOneDog(Dog dog);

    @Modifying
    @Query("DELETE FROM Image i " +
           "WHERE i.shelter = ?1 " +
           "AND i.id = ?2")
    void deleteImage(Shelter shelter, Long imgId);

    @Query("SELECT COUNT(i) " +
           "FROM Image i " +
           "WHERE i.dog.id = ?1")
    int getNumberOfPicsUploadedToDog(Long dogId);

    @Modifying
    @Query("DELETE FROM Image i " +
           "WHERE i.id = ?1")
    void deletePicture(Long pictureId);

}
