package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.AddressShelter;
import com.dogadopt.dog_adopt.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressShelterRepository extends JpaRepository<AddressShelter, Long> {

    @Query("SELECT ash " +
           "FROM AddressShelter ash " +
           "WHERE ash.address = ?1")
    AddressShelter findByAddress(Address address);

    @Modifying
    @Query("DELETE FROM AddressShelter ash " +
           "WHERE ash.shelter = ?1 " +
           "AND ash.address = ?2")
    void deleteConnectionBetweenShelterAndAddress(Shelter shelter, Address address);
}
