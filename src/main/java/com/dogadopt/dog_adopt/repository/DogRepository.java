package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoListOfDogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    @Query("SELECT d " +
           "FROM Dog d " +
           "WHERE d.shelter.id = ?1")
    List<Dog> getAllDogsFromShelter(Long shelterId);
}
