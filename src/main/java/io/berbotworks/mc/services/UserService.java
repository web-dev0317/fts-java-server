package io.berbotworks.mc.services;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.berbotworks.mc.exceptions.UserAlreadyExistsException;
import io.berbotworks.mc.models.User;
import io.berbotworks.mc.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User saveUser(User user) {
        String email = user.getEmail();
        if (userExists(email)) {
            throw new UserAlreadyExistsException(
                    new StringBuilder()
                            .append("User with given mail id : ")
                            .append(email)
                            .append(" already exists")
                            .toString());
        }
        user.setRoomId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ROLE_USER"));
        return userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUser(Long uid) {
        return userRepository.findById(uid).orElse(null);
    }
}
