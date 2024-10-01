package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
