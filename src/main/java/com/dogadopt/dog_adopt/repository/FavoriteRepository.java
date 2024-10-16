package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.DogAndUserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<DogAndUserFavorite, Long> {

    @Query("SELECT duf " +
           "FROM DogAndUserFavorite duf " +
           "WHERE duf.user = ?1 " +
           "AND duf.dog = ?2")
    DogAndUserFavorite findFavoriteByUserAndDog(AppUser user, Dog dog);

    @Query("SELECT duf " +
           "FROM DogAndUserFavorite duf " +
           "WHERE duf.user = ?1")
    List<DogAndUserFavorite> getFavoritesOfUser(AppUser user);
}
