package com.company.employeemanagement.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.employeemanagement.dto.AuthRequest;
import com.company.employeemanagement.dto.AuthResponse;
import com.company.employeemanagement.dto.UserDTO;
import com.company.employeemanagement.entity.Role;
import com.company.employeemanagement.entity.User;
import com.company.employeemanagement.repository.RoleRepository;
import com.company.employeemanagement.repository.UserRepository;
import com.company.employeemanagement.security.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public String registerUser(UserDTO userDTO){
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role role = roleRepository.findByName(userDTO.getRole()).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRoles(Set.of(role));
        newUser.setCreatedAt(java.time.LocalDateTime.now().toString());
        newUser.setUpdatedAt(java.time.LocalDateTime.now().toString());

        userRepository.save(newUser);

        return "User registered sucessfully!";
    }

    public AuthResponse authenticate(AuthRequest authRequest) {

        // 1️⃣ Fetch user by username
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // 2️⃣ Validate password
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // 3️⃣ Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // 4️⃣ Return AuthResponse with the token
        return new AuthResponse(token);
    }
}
