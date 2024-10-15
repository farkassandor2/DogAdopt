package com.dogadopt.dog_adopt.service.user;

import com.dogadopt.dog_adopt.config.security.AuthUserService;
import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.DogAndUserAdoption;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.AppUserUpdateCommand;
import com.dogadopt.dog_adopt.dto.incoming.ImageUploadCommand;
import com.dogadopt.dog_adopt.dto.outgoing.*;
import com.dogadopt.dog_adopt.exception.*;
import com.dogadopt.dog_adopt.registration.token.ConfirmationToken;
import com.dogadopt.dog_adopt.registration.token.ConfirmationTokenService;
import com.dogadopt.dog_adopt.repository.AppUserRepository;
import com.dogadopt.dog_adopt.service.dog.DogService;
import com.dogadopt.dog_adopt.service.image.ImageService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private static final String USER_FOLDER = "users";
    private static final ImageType USER_IMAGE = ImageType.USER;
    private static final String USER_NOT_ACTIVE_MESSAGE = "User not active with id ";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final ImageService imageService;
    private final DogService dogService;
    private final AuthUserService authUserService;
    private final ModelMapper modelMapper;

    @Override
    public String registerCustomer(AppUserCreateCommand command) {

        AppUser user;
        ConfirmationToken confirmationToken;

        try {
            user = modelMapper.map(command, AppUser.class);
            appUserRepository.save(user);

            String encodedPassword = encodePassword(command.getPassword());
            setPasswordToUser(user, encodedPassword);

            confirmationToken = confirmationTokenService.generateToken(user);

        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(command.getEmail());
        } catch (MappingException e) {
            throw new WrongCountryNameException(command.getCountry().getName());
        }
        return confirmationToken.getToken();
    }

    public void setPasswordToUser(AppUser user, String encodedPassword) {
        user.setPassword(encodedPassword);
    }

    public String encodePassword(@NotNull String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public void enableCustomer(@NonNull String email) {
        appUserRepository.enableUser(email);
    }

    @Override
    public AppUser findUserByEmail(String emailAddress) {
        AppUser user = appUserRepository.getUserByEmail(emailAddress);
        if (user == null) {
            throw new UserNotFoundException("User not found with e-mail " + emailAddress);
        }
        return user;
    }

    @Override
    public AppUserInfo updateUser(Long userId, AppUserUpdateCommand command) {

        AppUser user = findActiveUserById(userId);
        AppUser currentUser = getLoggedInCustomer();

        if (user != null && user == currentUser) {
            if (command.getEmail() != null) {
                user.setEmail(command.getEmail());
            }

            if (command.getPassword() != null) {
                user.setPassword(command.getPassword());
            }

            if (command.getCountry() != null) {
                user.setCountry(command.getCountry());
            }

            if (command.getFirstName() != null) {
                user.setFirstName(command.getFirstName());
            }

            if (command.getLastName() != null) {
                user.setLastName(command.getLastName());
            }

            if (command.getPhoneNumber() != null) {
                user.setPhoneNumber(command.getPhoneNumber());
            }

            appUserRepository.save(user);
            return modelMapper.map(user, AppUserInfo.class);

        } else throw new UserNotActiveException(USER_NOT_ACTIVE_MESSAGE + userId);
    }

    @Override
    public AppUser findUserByToken(String token) {
        return appUserRepository.getUserByToken(token);
    }

    @Override
    public void uploadPictureForUser(Long userId, ImageUploadCommand command) {

        AppUser user = findActiveUserById(userId);
        AppUser currentUser = getLoggedInCustomer();

        if (user != null && user == currentUser) {
            List<MultipartFile> multipartFiles = command.getImages();

            if (multipartFiles != null && !multipartFiles.isEmpty()) {
                saveImagesOfUser(multipartFiles, user);
            }
        } else throw new UserNotActiveException(USER_NOT_ACTIVE_MESSAGE + userId);

    }

    @Override
    public void deletePictureForUser(Long pictureId) {
        imageService.deleteImage(pictureId, USER_FOLDER);
    }

    @Override
    public void deleteUser(Long userId) {

        AppUser user = findActiveUserById(userId);
        AppUser currentUser = getLoggedInCustomer();

        if (user != null && user == currentUser) {
            user.setActive(false);
        } else throw new UserNotActiveException(USER_NOT_ACTIVE_MESSAGE + userId);

    }

    public AppUser getLoggedInCustomer() {
        UserDetails userDetails = authUserService.getUserFromSession();
        return findUserByEmail(userDetails.getUsername());
    }

    @Override
    public AppUserInfo getOwnProfileOfUser() {

        AppUser user = getLoggedInCustomer();

        if (user != null) {
            if (user.isActive()) {
                AppUserInfo info = modelMapper.map(user, AppUserInfo.class);

                List<Dog> favoriteDogs = dogService.getFavoriteDogsOfUser(user);
                List<FavoriteInfo> favoriteInfos = getDogAndUserFavoriteInfos(favoriteDogs);
                info.setFavoriteInfos(favoriteInfos);

                List<Dog> adoptedDogs = dogService.getAdoptedDogsOfUser(user);
                List<AdoptionInfo> adoptionInfos = getDogAndUserAdoptionInfos(user, adoptedDogs);
                info.setAdoptionInfos(adoptionInfos);

                //TODO donationInfos

                //TODO walkingReservatoinInfos

                return info;
            } else {
                throw new AccountHasNotBeenActivatedYetException("Account has not been activated!");
            }
        } else {
            throw new IncorrectUsernameOrPasswordException("Incorrect username or password!");
        }
    }

    @Override
    public boolean checkIfEmailAlreadyRegisteredAndInactive(String email) {
        return appUserRepository.checkIfEmailAlreadyRegisteredAndInactive(email);
    }

    private List<AdoptionInfo> getDogAndUserAdoptionInfos(AppUser user, List<Dog> adoptedDogs) {

        List<DogAndUserAdoption> adoptions = appUserRepository.getAdoptionsOfUser(user);
        Map<Long, DogAndUserAdoption> adoptionsMap = adoptions.stream()
                                                              .collect(Collectors.toMap(adoption -> adoption.getDog().getId(), adoption -> adoption));

        return adoptedDogs.stream().map(dog -> {
            DogInfoOneDog dogInfo = dogService.getDogInfoOneDog(dog);

            AdoptionInfo adoptionInfo = new AdoptionInfo();
            adoptionInfo.setId(dogInfo.getId());
            adoptionInfo.setDogInfo(dogInfo);
            adoptionInfo.setUserId(user.getId());

            DogAndUserAdoption adoption = adoptionsMap.get(dog.getId());
            if (adoption != null) {
                adoptionInfo.setAdoptionType(adoption.getAdoptionType());
                adoptionInfo.setAdoptionStatus(adoption.getAdoptionStatus());
                adoptionInfo.setCreatedAt(adoption.getCreatedAt());
            }

            return adoptionInfo;
        }).toList();
    }

    private List<FavoriteInfo> getDogAndUserFavoriteInfos(List<Dog> favoriteDogs) {
        return favoriteDogs.stream().map(dog -> {

            DogInfoOneDog dogInfo = dogService.getDogInfoOneDog(dog);

            dogInfo.setDescription(dog.getDescription());

            FavoriteInfo favoriteInfo = new FavoriteInfo();
            favoriteInfo.setId(dogInfo.getId());
            favoriteInfo.setDogInfo(dogInfo);

            return favoriteInfo;
        }).toList();
    }

    private void saveImagesOfUser(List<MultipartFile> multipartFiles, AppUser user) {
        if (!multipartFiles.isEmpty()) {
            List<Image> images = imageService.uploadFile(multipartFiles, USER_FOLDER, user.getId(), USER_IMAGE);
            setUserToImageAndImageToUser(user, images);
        }
    }

    private void setUserToImageAndImageToUser(AppUser user, List<Image> images) {
        user.setImages(images);
        images.forEach(i -> i.setUser(user));
    }

    private AppUser findUserById(Long userId) {
        return appUserRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
    }

    public AppUser findActiveUserById(Long userId) {
        AppUser user = findUserById(userId);
        return user.isActive() ? user : null;
    }
}
