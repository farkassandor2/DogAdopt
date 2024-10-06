package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.enums.dog.Status;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoListOfDogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    @Query("SELECT d " +
           "FROM Dog d " +
           "WHERE d.shelter.id = ?1 AND d.status != ?2")
    List<Dog> getAllDogsFromShelter(Long shelterId, Status status);

    @Query("SELECT d " +
           "FROM Dog d " +
           "WHERE d.status != ?1")
    List<Dog> findAllNotDeceased(Status status);

    @Query("SELECT d " +
           "FROM Dog d " +
           "WHERE d.id = ?1 AND d.status != ?2")
    Optional<Object> findByIdNotDeceased(Long dogId, Status status);

    @Query("SELECT d " +
           "FROM Dog d " +
           "WHERE d.status NOT IN :statusesToLeaveOut")
    Stream<Dog> streamAllDogs(@Param("statusesToLeaveOut") List<Status> statusesToLeaveOut);
}
