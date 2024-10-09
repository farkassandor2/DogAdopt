package com.dogadopt.dog_adopt.service.user;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.AppUserUpdateCommand;
import com.dogadopt.dog_adopt.exception.UserAlreadyExistsException;
import com.dogadopt.dog_adopt.exception.UserNotFoundException;
import com.dogadopt.dog_adopt.exception.WrongCountryNameException;
import com.dogadopt.dog_adopt.registration.token.ConfirmationToken;
import com.dogadopt.dog_adopt.registration.token.ConfirmationTokenService;
import com.dogadopt.dog_adopt.repository.AppUserRepository;
import com.dogadopt.dog_adopt.validation.password.Password;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
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
    public AppUser getUserByEmail(String emailAddress) {
        AppUser user = appUserRepository.getUserByEmail(emailAddress);
        if (user == null) {
            throw new UserNotFoundException(emailAddress);
        }
        return user;
    }

    @Override
    public String updateUser(AppUserUpdateCommand command) {
        return "";
    }


    @Override
    public AppUser getUserByToken(String token) {
        return appUserRepository.getUserByToken(token);
    }
}
