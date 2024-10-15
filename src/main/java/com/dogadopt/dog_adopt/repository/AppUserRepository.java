package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.DogAndUserAdoption;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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
           "au.isActive = TRUE, " +
           "au.accountNonLocked = TRUE " +
           "WHERE au.email = ?1")
    void enableUser(@NonNull String email);

    @Query("SELECT au " +
           "FROM AppUser au " +
           "WHERE au.email = ?1")
    AppUser getUserByEmail(String emailAddress);

    @Query("SELECT au " +
           "FROM AppUser au " +
           "JOIN au.confirmationTokens ct " +
           "WHERE ct.token = ?1")
    AppUser getUserByToken(String token);

    @Query("SELECT a " +
           "FROM AppUser au " +
           "JOIN au.dogAndUserAdoptions a " +
           "WHERE a.user = ?1")
    List<DogAndUserAdoption> getAdoptionsOfUser(AppUser user);

    @Query("SELECT CASE WHEN COUNT(au) > 0  " +
           "THEN TRUE ELSE FALSE END " +
           "FROM AppUser au " +
           "WHERE au.email = ?1 " +
           "AND au.isActive = FALSE")
    boolean checkIfEmailAlreadyRegisteredAndInactive(String email);
}
