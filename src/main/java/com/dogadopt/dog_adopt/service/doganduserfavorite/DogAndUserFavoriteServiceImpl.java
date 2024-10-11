package com.dogadopt.dog_adopt.service.doganduserfavorite;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.DogAndUserFavorite;
import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserFavoriteInfo;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.exception.DogCannotBeAddedToFavoritesException;
import com.dogadopt.dog_adopt.exception.DogIsAlreadyOnFavoriteListOfUserException;
import com.dogadopt.dog_adopt.repository.DogAndUserFavoriteRepository;
import com.dogadopt.dog_adopt.service.dog.DogService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DogAndUserFavoriteServiceImpl implements DogAndUserFavoriteService{

    private final DogAndUserFavoriteRepository dogAndUserFavoriteRepository;
    private final AppUserService appUserService;
    private final DogService dogService;
    private final ModelMapper modelMapper;

    @Override
    public DogAndUserFavoriteInfo addNewFavorite(Long userId, Long dogId) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser currentUser = appUserService.getLoggedInCustomer();
        Dog dog = dogService.getOneDog(dogId);
        DogAndUserFavorite favorite = new DogAndUserFavorite();

        if (user != null && dog != null && user == currentUser) {
            boolean dogIsNotOnFavoriteListOfUser = checkFavoriteList(user, dog);
            if (dogIsNotOnFavoriteListOfUser) {
                favorite.setUser(user);
                favorite.setDog(dog);
                dog.setDogAndUserFavorite(new ArrayList<>(List.of(favorite)));
                user.setDogAndUserFavorite(new ArrayList<>(List.of(favorite)));
                dogAndUserFavoriteRepository.save(favorite);

                DogAndUserFavoriteInfo info = modelMapper.map(favorite, DogAndUserFavoriteInfo.class);
                info.setDogInfo(modelMapper.map(dog, DogInfoOneDog.class));
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

    private boolean checkFavoriteList(AppUser user, Dog dog) {
        DogAndUserFavorite favorite = dogAndUserFavoriteRepository.findFavoriteByUserAndDog(user, dog);
        return favorite == null;
    }
}
