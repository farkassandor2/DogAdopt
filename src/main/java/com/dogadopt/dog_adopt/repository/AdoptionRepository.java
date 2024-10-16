package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.DogAndUserAdoption;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdoptionRepository extends JpaRepository<DogAndUserAdoption, Long> {

    @Query("SELECT CASE WHEN COUNT(dua) > 0 " +
           "THEN TRUE ELSE FALSE END " +
           "FROM DogAndUserAdoption dua " +
           "WHERE dua.adoptionType = ?1 AND dua.dog = ?2")
    boolean isDogRealAdopted(AdoptionType adoptionType, Dog dog);

    @Query("SELECT dua " +
           "FROM DogAndUserAdoption dua " +
           "WHERE dua.user = ?1")
    List<DogAndUserAdoption> getAdoptionOfUser(AppUser user);
}
