package com.chatapp.realtimechatapp.service;

import com.chatapp.realtimechatapp.dto.LoginRequestDTO;
import com.chatapp.realtimechatapp.dto.LoginResponseDTO;
import com.chatapp.realtimechatapp.dto.RegisterRequestDTO;
import com.chatapp.realtimechatapp.dto.UserDTO;
import com.chatapp.realtimechatapp.model.User;
import com.chatapp.realtimechatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO signup (RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already in use");
        }

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode((registerRequestDTO.getPassword())));
        user.setEmail(registerRequestDTO.getEmail());

        User savedUser = userRepository.save(user);

        return convertToUserDTO(user);
    }

    public LoginResponseDTO login (LoginRequestDTO loginRequestDTO) {

        return null;

    }


    public UserDTO convertToUserDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }
}
