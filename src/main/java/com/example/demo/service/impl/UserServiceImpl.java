package com.example.demo.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
    public User registerUser(UserRegistrationDto dto) {
//        User user = User.builder()
//                .name(dto.getName())
//                .email(dto.getEmail())
//                .contact(dto.getContact())
//                .password(passwordEncoder.encode(dto.getPassword()))
//                .role(dto.getRole())
//                .build();
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setContact(dto.getContact());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(dto.getRole());
        return userRepository.save(user);
    }
}
