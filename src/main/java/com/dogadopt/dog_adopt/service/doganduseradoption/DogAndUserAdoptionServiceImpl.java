package com.dogadopt.dog_adopt.service.doganduseradoption;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.DogAndUserAdoption;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionStatus;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionType;
import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserAdoptionInfo;
import com.dogadopt.dog_adopt.exception.DogAlreadyAdoptedInRealLifeException;
import com.dogadopt.dog_adopt.exception.WrongCredentialsException;
import com.dogadopt.dog_adopt.repository.DogAndUserAdoptionRepository;
import com.dogadopt.dog_adopt.service.dog.DogService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class DogAndUserAdoptionServiceImpl implements DogAndUserAdoptionService{

    private final DogAndUserAdoptionRepository dogAndUserAdoptionRepository;
    private final AppUserService appUserService;
    private final DogService dogService;
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
                if (user != null && user == loggedInCustomer) {
                    setUserAndDogToAdoption(adoption, user, dog, adoptionType);
                    if (adoptionType == AdoptionType.VIRTUAL) {
                        adoption.setAdoptionStatus(AdoptionStatus.FULFILLED);
                    } else if (adoptionType == AdoptionType.REAL) {
                        adoption.setAdoptionStatus(AdoptionStatus.PENDING);
                        //TODO
                        //!!!! Send email to ADMIN to notify about the real adoption request
                        //Send email to user that we took request
                    }
                } else {
                    throw new WrongCredentialsException("Wrong credentials!");
                }
            } else {
                throw new DogAlreadyAdoptedInRealLifeException(
                        "Dog with id " + dogId + " has already been adopted by a loving family!");
            }
        }
        dogAndUserAdoptionRepository.save(adoption);

        DogAndUserAdoptionInfo info = modelMapper.map(adoption, DogAndUserAdoptionInfo.class);
        info.setUserId(userId);
        info.setDogId(dogId);
        return info;
    }

    private void setUserAndDogToAdoption(DogAndUserAdoption adoption,
                                         AppUser user, Dog dog,
                                         AdoptionType adoptionType) {
        adoption.setUser(user);
        adoption.setDog(dog);
        adoption.setAdoptionType(adoptionType);
    }

    private boolean checkIfRealAdopted(Dog dog) {
        return dogAndUserAdoptionRepository.isDogRealAdopted(AdoptionType.REAL, dog);
    }
}
