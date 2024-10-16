package com.dogadopt.dog_adopt.service.favorite;

import com.dogadopt.dog_adopt.config.ObjectMapperUtil;
import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.DogAndUserFavorite;
import com.dogadopt.dog_adopt.dto.outgoing.FavoriteInfo;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.exception.*;
import com.dogadopt.dog_adopt.repository.FavoriteRepository;
import com.dogadopt.dog_adopt.service.dog.DogService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final AppUserService appUserService;
    private final DogService dogService;
    private final ModelMapper modelMapper;

    @Override
    public FavoriteInfo addNewFavorite(Long userId, Long dogId) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser currentUser = appUserService.getLoggedInCustomer();
        Dog dog = dogService.getOneDog(dogId);
        DogAndUserFavorite favorite = new DogAndUserFavorite();

        if (user != null && dog != null && user == currentUser) {
            Long favId = checkFavoriteList(user, dog);
            if (favId == null) {
                setUserAndDogToFavorite(favorite, user, dog);
                favoriteRepository.save(favorite);

                FavoriteInfo info = modelMapper.map(favorite, FavoriteInfo.class);
                DogInfoOneDog dogInfo = dogService.getDogInfoOneDog(dog);
                info.setDogInfo(dogInfo);
                return info;
            } else {
                throw new DogIsAlreadyOnFavoriteListOfUserException(
                        "Dog with id " + dogId + " is already added to the favorite list of user with id " + userId);
            }
        } else {
            throw new DogCannotBeAddedToFavoritesException(
                    "Dog with id " + dogId + " cannot be added to user with id " + userId);
        }
    }

    private void setUserAndDogToFavorite(DogAndUserFavorite favorite, AppUser user, Dog dog) {
        favorite.setUser(user);
        favorite.setDog(dog);
        dog.setDogAndUserFavorites(new ArrayList<>(List.of(favorite)));
        user.setDogAndUserFavorites(new ArrayList<>(List.of(favorite)));
    }

    @Override
    public void deleteDogFromFavorite(Long userId, Long dogId) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser currentUser = appUserService.getLoggedInCustomer();
        Dog dog = dogService.getOneDog(dogId);

        if (user != null && dog != null && user == currentUser) {
            Long favId = checkFavoriteList(user, dog);
            if (favId != null) {
                favoriteRepository.deleteById(favId);
            } else {
                throw new DogIsNotOnTheFavoriteListOfUserException(
                        "Dog with id " + dogId + " is not on the favorite list of user with id " + userId);
            }
        } else {
            throw new DogCannotBeRemovedFromFavoritesException(
                    "Dog with id " + dogId + " cannot be removed from favorite list of user with id " + userId);
        }
    }

    @Override
    public List<FavoriteInfo> getFavoritesOfUser(Long userId) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser currentUser = appUserService.getLoggedInCustomer();

        if (user != null && user == currentUser) {
            return getFavoriteInfos(user);
        } else {
            throw new WrongCredentialsException("Wrong credentials.");
        }
    }

    private List<FavoriteInfo> getFavoriteInfos(AppUser user) {
        List<DogAndUserFavorite> favorites = favoriteRepository.getFavoritesOfUser(user);
        List<FavoriteInfo> favoriteInfos = ObjectMapperUtil.mapAll(favorites, FavoriteInfo.class);

        List<DogInfoOneDog> dogInfos = favorites.stream()
                .map(DogAndUserFavorite::getDog)
                .map(dog -> modelMapper.map(dog, DogInfoOneDog.class))
                .toList();

        IntStream.range(0, favoriteInfos.size())
                 .forEach(i -> favoriteInfos.get(i).setDogInfo(dogInfos.get(i)));

        return favoriteInfos;
    }

    private Long checkFavoriteList(AppUser user, Dog dog) {
        DogAndUserFavorite favorite = favoriteRepository.findFavoriteByUserAndDog(user, dog);
        if (favorite != null) {
            return favorite.getId();
        } else {
            return null;
        }
    }
}
