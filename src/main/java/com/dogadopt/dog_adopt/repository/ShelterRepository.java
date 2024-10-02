package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
