package com.dictionaryapp.service.Implementation;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.LoginUserDTO;
import com.dictionaryapp.model.dto.RegisterUserDTO;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.repo.UserRepository;
import com.dictionaryapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }


    @Override
    public boolean register(RegisterUserDTO registerUserDTO) {

        //checking if the username or the email already exists
        if (userRepository.existsByUsernameOrEmail(registerUserDTO.getUsername(), registerUserDTO.getEmail())) {
            return false;
        }

        //checking if the given pass and confirm pass are equals
        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) {
            return false;
        }

        User user = modelMapper.map(registerUserDTO, User.class);

        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean login(LoginUserDTO loginUserDTO) {

        Optional<User> byUsername = userRepository.findByUsername(loginUserDTO.getUsername());

        if (byUsername.isEmpty()) {
            return false;
        }

        if (!passwordEncoder.matches(loginUserDTO.getPassword(), byUsername.get().getPassword())) {
            return false;
        }

        userSession.login(byUsername.get());

        return true;
    }
}
