package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.AddressShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressShelterRepository extends JpaRepository<AddressShelter, Long> {

}
