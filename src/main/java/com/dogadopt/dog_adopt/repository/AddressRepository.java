package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.enums.address.Country;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a " +
           "FROM Address a " +
           "WHERE a.zip = ?1 " +
           "AND a.country = ?2 " +
           "AND a.city = ?3 " +
           "AND a.street = ?4 " +
           "AND a.houseNumber = ?5")
    Address findByAllArgs(String zip,
                          Country country,
                          String city,
                          String street,
                          String houseNumber);
}
