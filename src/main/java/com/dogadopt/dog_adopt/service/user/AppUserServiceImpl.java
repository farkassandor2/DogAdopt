package com.dogadopt.dog_adopt.service.user;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.AppUserUpdateCommand;
import com.dogadopt.dog_adopt.exception.UserAlreadyExistsException;
import com.dogadopt.dog_adopt.exception.WrongCountryNameException;
import com.dogadopt.dog_adopt.registration.token.ConfirmationToken;
import com.dogadopt.dog_adopt.registration.token.ConfirmationTokenService;
import com.dogadopt.dog_adopt.repository.AppUserRepository;
import jakarta.transaction.Transactional;
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

            user.setPassword(bCryptPasswordEncoder.encode(command.getPassword()));

            confirmationToken = confirmationTokenService.generateToken(user);

        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(command.getEmail());
        } catch (MappingException e) {
            throw new WrongCountryNameException(command.getCountry().getName());
        }
        return confirmationToken.getToken();
    }

    @Override
    public String updateUser(AppUserUpdateCommand command) {
        return "";
    }
}
