package com.dictionaryapp.service.Implementation;

import com.dictionaryapp.model.dto.RegisterUserDTO;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.repo.UserRepository;
import com.dictionaryapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean register(RegisterUserDTO registerUserDTO) {

        //checking if the username or the email already exists
        if (userRepository.existsByUsernameAndEmail(registerUserDTO.getUsername(), registerUserDTO.getEmail())) {
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
}
