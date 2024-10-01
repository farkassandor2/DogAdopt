package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
