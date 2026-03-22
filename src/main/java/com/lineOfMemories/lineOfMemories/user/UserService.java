package com.lineOfMemories.lineOfMemories.user;

import com.lineOfMemories.lineOfMemories.Role;
import com.lineOfMemories.lineOfMemories.admin.LoginRequest;
import com.lineOfMemories.lineOfMemories.configuration.JwtService;
import com.lineOfMemories.lineOfMemories.exceptions.UnauthorizedOperationException;
import com.lineOfMemories.lineOfMemories.mapper.EntityMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EntityMapper entityMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                       EntityMapper entityMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.entityMapper = entityMapper;
    }

    public UserDTO createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EntityExistsException("This email is registered " + user.getEmail());
        user.setRole(Role.USER);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return entityMapper.mapUserToUserDTO(userRepository.save(user));

    }

    public String userAuthorization(LoginRequest loginRequest) {
        User registeredUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("Admin with this email not exist " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), registeredUser.getPassword())) {
            throw new UnauthorizedOperationException("Incorrect password");
        }
        return jwtService.generateToken(registeredUser);
    }
}

