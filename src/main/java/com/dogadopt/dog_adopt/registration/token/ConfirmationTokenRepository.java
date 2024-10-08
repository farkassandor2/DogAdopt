package com.dogadopt.dog_adopt.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query("SELECT ct " +
           "FROM ConfirmationToken ct " +
           "WHERE ct.token = ?1")
    Optional<ConfirmationToken> getTokenByString(String token);

    @Modifying
    @Query("UPDATE ConfirmationToken c " +
           "SET c.confirmedAt = ?2 " +
           "WHERE c.token = ?1")
    void setConfirmedAtToNow(String token, LocalDateTime localDateTime);

}
