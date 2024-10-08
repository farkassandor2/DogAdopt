package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.AppUser;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT au " +
           "FROM AppUser au " +
           "WHERE au.email = :email")
    Optional<Object> findByEmail(String email);

    @Modifying
    @Query("UPDATE AppUser au " +
           "SET au.enabled = TRUE, " +
           "au.accountNonLocked = TRUE " +
           "WHERE au.email = ?1")
    void enableUser(@NonNull String email);
}
