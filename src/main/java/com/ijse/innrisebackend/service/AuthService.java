package com.ijse.innrisebackend.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.ijse.innrisebackend.dto.AuthDTO;
import com.ijse.innrisebackend.dto.AuthResponseDTO;
import com.ijse.innrisebackend.dto.RegisterDTO;
import com.ijse.innrisebackend.entity.User;
import com.ijse.innrisebackend.enums.Role;
import com.ijse.innrisebackend.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    // Use a strong 256-bit key (32+ characters)
    private final String JWT_SECRET = "rTVm7y3g5c2h8vD0fX9wK1s3uT4qP6lN"; // <- change to your own secure key
    private final long JWT_EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

    // -------------------- REGISTER --------------------
    public User register(RegisterDTO registerDTO) {
        if (authRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.valueOf(registerDTO.getRole().toUpperCase()))
                .build();

        return authRepository.save(user);
    }

    // -------------------- LOGIN --------------------
    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        Optional<User> userOpt = authRepository.findByEmail(authDTO.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token with proper key
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO authenticateGoogle(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance() //  use GsonFactory instead of Jackson
            )
                    .setAudience(Collections.singletonList("427810863490-vvr3a2jimu7ki8du7up4ofatfteqrit0.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String firstName = (String) payload.get("given_name");
                String lastName = (String) payload.get("family_name");

                User user = authRepository.findByEmail(email)
                        .orElseGet(() -> {
                            User newUser = User.builder()
                                    .firstName(firstName)
                                    .lastName(lastName)
                                    .email(email)
                                    .password("") // Google login, no password
                                    .role(Role.USER)
                                    .build();
                            return authRepository.save(newUser);
                        });

                String token = Jwts.builder()
                        .setSubject(user.getEmail())
                        .claim("role", user.getRole().name())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                        .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()), SignatureAlgorithm.HS256)
                        .compact();

                return new AuthResponseDTO(token);
            } else {
                throw new RuntimeException("Invalid Google ID token");
            }

        } catch (Exception e) {
            e.printStackTrace(); // 🔍 see actual error
            throw new RuntimeException("Google authentication failed", e);
        }
    }
}
