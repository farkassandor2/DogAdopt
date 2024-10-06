package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
