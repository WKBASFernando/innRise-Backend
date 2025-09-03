package com.ijse.innrisebackend.service;

import com.ijse.innrisebackend.dto.AuthDTO;
import com.ijse.innrisebackend.dto.RegisterDTO;
import com.ijse.innrisebackend.dto.AuthResponseDTO;
import com.ijse.innrisebackend.entity.Role;
import com.ijse.innrisebackend.entity.User;
import com.ijse.innrisebackend.repository.UserRepository;
import com.ijse.innrisebackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        User user=userRepository.findByEmail(authDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (!passwordEncoder.matches(authDTO.getPassword(),user.getPassword())) {
            throw new BadCredentialsException("Incorrect username or password!");
        }
        String token = jwtUtil.generateToken(authDTO.getEmail());
        return new AuthResponseDTO(token);
    }

    public String register(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        User user = User.builder()
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.valueOf(registerDTO.getRole()))
                .build();
        userRepository.save(user);
        return "User " + registerDTO.getFirstName() + " " + registerDTO.getLastName() + " registered successfully!";
    }
}
