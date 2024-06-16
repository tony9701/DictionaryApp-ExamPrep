package com.dictionaryapp.service;

import com.dictionaryapp.model.dto.RegisterUserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean register(RegisterUserDTO registerUserDTO);
}
