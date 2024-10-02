package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
