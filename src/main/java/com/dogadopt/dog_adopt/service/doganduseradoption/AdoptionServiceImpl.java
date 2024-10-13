package com.dogadopt.dog_adopt.service.doganduseradoption;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.DogAndUserAdoption;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionStatus;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionType;
import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserAdoptionInfo;
import com.dogadopt.dog_adopt.email.build.EmailTemplateService;
import com.dogadopt.dog_adopt.email.send.EmailSenderService;
import com.dogadopt.dog_adopt.exception.DogAlreadyAdoptedInRealLifeException;
import com.dogadopt.dog_adopt.exception.WrongCredentialsException;
import com.dogadopt.dog_adopt.repository.AdoptionRepository;
import com.dogadopt.dog_adopt.service.dog.DogService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

    private static final String ADMIN_EMAIL = "farkas.sandor2@gmail.com";

    private final AdoptionRepository adoptionRepository;
    private final AppUserService appUserService;
    private final DogService dogService;
    private final EmailSenderService emailSenderService;
    private final EmailTemplateService emailTemplateService;
    private final ModelMapper modelMapper;

    @Override
    public DogAndUserAdoptionInfo adopt(Long userId, Long dogId, DogAndUserAdoptionCreateCommand command) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser loggedInCustomer = appUserService.getLoggedInCustomer();
        Dog dog = dogService.getOneDog(dogId);
        AdoptionType adoptionType = command.getAdoptionType();
        DogAndUserAdoption adoption = new DogAndUserAdoption();

        if(dog != null) {
            boolean dogIsRealAdopted = checkIfRealAdopted(dog);
            if (!dogIsRealAdopted) {
                if (user == loggedInCustomer) {
                    setUserAndDogToAdoption(adoption, user, dog, adoptionType);
                    if (adoptionType == AdoptionType.VIRTUAL) {
                        adoption.setAdoptionStatus(AdoptionStatus.FULFILLED);
                    } else if (adoptionType == AdoptionType.REAL) {
                        adoption.setAdoptionStatus(AdoptionStatus.PENDING);
                        sendEmailToAdmin(user);
                        sendEmailToUser(user);
                    }
                } else {
                    throw new WrongCredentialsException("Wrong credentials!");
                }
            } else {
                throw new DogAlreadyAdoptedInRealLifeException(
                        "Dog with id " + dogId + " has already been adopted by a loving family!");
            }
        }
        adoptionRepository.save(adoption);

        DogAndUserAdoptionInfo info = modelMapper.map(adoption, DogAndUserAdoptionInfo.class);
        info.setUserId(userId);
        info.setDogId(dogId);
        return info;
    }

    @Override
    public void deleteAdoption(Long adoptionId) {

    }

    private void sendEmailToUser(AppUser user) {

        String letterTitleAdopter = "Congratulations! Your Adoption Process Started!";
        String emailAdopter = user.getEmail();
        String textToAdopter1 = "We have received your adoption request!";
        String textToAdopter2 = "Please make sure that you have provided your contact information on your profile.";
        //TODO change to real URL to login page
        String linkToAdopter = "https://google.com";
        String textToAdopter3 = "Soon we will inform you about the process of the adoption.";

        String emailContentAdopter = emailTemplateService.buildConfirmationEmail(letterTitleAdopter,
                                                                                 user.getEmail(),
                                                                                 textToAdopter1,
                                                                                 textToAdopter2,
                                                                                 textToAdopter3,
                                                                                 linkToAdopter);
        emailSenderService.send(emailAdopter,
                                emailContentAdopter,
                                "Adoption Request Received");
    }

    private void sendEmailToAdmin(AppUser user) {

        String letterTitleAdmin = "Handle Adoption Request";
        String textToAdmin1 = "There is a real adoption request from user with userId " + user.getId();
        String textToAdmin2 = "Handle request on this link";
        //TODO change to real URL to guide us to request
        String linkToAdmin = "https://google.com";
        String textToAdmin3 = "";

        String emailContentAdmin = emailTemplateService.buildConfirmationEmail(letterTitleAdmin,
                                                                               "DogAdopt Admin",
                                                                               textToAdmin1,
                                                                               textToAdmin2,
                                                                               textToAdmin3,
                                                                               linkToAdmin);
        emailSenderService.send(ADMIN_EMAIL,
                                emailContentAdmin,
                                "Real Adoption Request");
    }

    private void setUserAndDogToAdoption(DogAndUserAdoption adoption,
                                         AppUser user, Dog dog,
                                         AdoptionType adoptionType) {
        adoption.setUser(user);
        adoption.setDog(dog);
        adoption.setAdoptionType(adoptionType);
    }

    private boolean checkIfRealAdopted(Dog dog) {
        return adoptionRepository.isDogRealAdopted(AdoptionType.REAL, dog);
    }
}
