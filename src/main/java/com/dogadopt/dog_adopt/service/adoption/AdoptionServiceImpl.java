package com.dogadopt.dog_adopt.service.adoption;

import com.dogadopt.dog_adopt.config.ObjectMapperUtil;
import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.DogAndUserAdoption;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionStatus;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionType;
import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AdoptionInfo;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.email.build.EmailTemplateService;
import com.dogadopt.dog_adopt.email.send.EmailSenderService;
import com.dogadopt.dog_adopt.exception.AdoptionNotFoundException;
import com.dogadopt.dog_adopt.exception.CannotDeleteAdoptionException;
import com.dogadopt.dog_adopt.exception.DogAlreadyAdoptedInRealLifeException;
import com.dogadopt.dog_adopt.exception.WrongCredentialsException;
import com.dogadopt.dog_adopt.repository.AdoptionRepository;
import com.dogadopt.dog_adopt.service.dog.DogService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


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
    public AdoptionInfo adopt(Long userId, Long dogId, DogAndUserAdoptionCreateCommand command) {

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

        AdoptionInfo info = modelMapper.map(adoption, AdoptionInfo.class);
        info.setUserId(userId);

        DogInfoOneDog dogInfo = dogService.getDogInfoOneDog(dog);
        info.setDogInfo(dogInfo);

        return info;
    }

    @Override
    public void deleteAdoptionByUser(Long adoptionId) {
        DogAndUserAdoption adoption = getAdoptionById(adoptionId);

        if (adoption.getAdoptionType() != AdoptionType.REAL && adoption.getAdoptionStatus() != AdoptionStatus.FULFILLED) {
            adoptionRepository.deleteById(adoptionId);
        } else {
            throw new CannotDeleteAdoptionException("Adoption with id " + adoptionId + " is fulfilled and cannot be deleted");
        }
    }

    @Override
    public Map<String, String> updateAdoption(Long adoptionId, Map<String, Object> updates) {

        Map<String, String> reply = new HashMap<>();
        DogAndUserAdoption adoption = getAdoptionById(adoptionId);

        if (adoption != null) {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "adoptionType":
                        adoption.setAdoptionType(AdoptionType.valueOf(value.toString()));
                        break;
                    case "adoptionStatus":
                        adoption.setAdoptionStatus(AdoptionStatus.valueOf(value.toString()));
                        break;
                    default:
                        throw  new ResponseStatusException(BAD_REQUEST, "Unknown field: " + key);
                }
            });
        } else {
            reply.put("message", "Adoption not found");
        }
        reply.put("message", "Update successful!");
        return reply;
    }

    @Override
    public void deleteAdoptionByAdmin(Long adoptionId) {
        adoptionRepository.deleteById(adoptionId);
    }

    @Override
    public List<AdoptionInfo> getAdoptionsOfUserByUser(Long userId) {
        AppUser user = appUserService.findActiveUserById(userId);
        AppUser loggedInUser = appUserService.getLoggedInCustomer();

        if (user != null && user == loggedInUser) {
            return getAdoptionInfosForUser(user);
        } else {
            throw new WrongCredentialsException("Wrong credentials");
        }
    }

    private List<AdoptionInfo> getAdoptionInfosForUser(AppUser user) {
        List<DogAndUserAdoption> adoptions = adoptionRepository.getAdoptionOfUser(user);
        return getAdoptionInfos(adoptions);
    }

    @Override
    public List<AdoptionInfo> getAllAdoptionInfosByAdmin() {
        List<DogAndUserAdoption> adoptions = adoptionRepository.findAll();
        return getAdoptionInfos(adoptions);
    }

    private List<AdoptionInfo> getAdoptionInfos(List<DogAndUserAdoption> adoptions) {
        List<AdoptionInfo> adoptionInfos = ObjectMapperUtil.mapAll(adoptions, AdoptionInfo.class);

        List<Dog> dogs = adoptions.stream()
                                  .map(DogAndUserAdoption::getDog)
                                  .toList();

        List<DogInfoOneDog> dogInfos = dogs.stream()
                .map(dog -> modelMapper.map(dog, DogInfoOneDog.class))
                .toList();

        IntStream.range(0, adoptionInfos.size())
                .forEach(i -> {
                    adoptionInfos.get(i).setDogInfo(dogInfos.get(i));
                    adoptionInfos.get(i).setUserId(adoptions.get(i).getUser().getId());
                });
        return adoptionInfos;
    }

    private DogAndUserAdoption getAdoptionById(Long adoptionId) {
        return adoptionRepository
                .findById(adoptionId)
                .orElseThrow(() -> new AdoptionNotFoundException("Adoption not found wiht id " + adoptionId));
    }

    private void sendEmailToUser(AppUser user) {

        String letterTitleAdopter = "Congratulations! Your Adoption Process Started!";
        String emailAdopter = user.getEmail();
        String textToAdopter1 = "We have received your adoption request!";
        String textToAdopter2 = "Please make sure that you have provided your contact information on your profile.";
        //TODO change to real URL to login page
        String linkToAdopter = "https://google.com";
        String textToAdopter3 = "Soon we will inform you about the process of the adoption.";

        String emailContentAdopter = emailTemplateService.buildEmail(letterTitleAdopter,
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
        //TODO change to real URL to guide us to request + add param adoptionId
        String linkToAdmin = "https://google.com";
        String textToAdmin3 = "";

        String emailContentAdmin = emailTemplateService.buildEmail(letterTitleAdmin,
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
