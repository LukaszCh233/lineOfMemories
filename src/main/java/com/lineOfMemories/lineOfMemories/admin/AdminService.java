package com.lineOfMemories.lineOfMemories.admin;

import com.lineOfMemories.lineOfMemories.Role;
import com.lineOfMemories.lineOfMemories.configuration.JwtService;
import com.lineOfMemories.lineOfMemories.exceptions.UnauthorizedOperationException;
import com.lineOfMemories.lineOfMemories.mapper.EntityMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EntityMapper entityMapper;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                        EntityMapper entityMapper) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.entityMapper = entityMapper;
    }

    public AdminDTO createAdmin(Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent())
            throw new EntityExistsException("This email is registered " + admin.getEmail());
        admin.setRole(Role.ADMIN);
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);

        return entityMapper
                .mapAdminToAdminDTO(adminRepository.save(admin));

    }

    public String adminAuthorization(LoginRequest loginRequest) {
        Admin registeredAdmin = adminRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("Admin with this email not exist " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), registeredAdmin.getPassword())) {
            throw new UnauthorizedOperationException("Incorrect password");
        }
        return jwtService.generateToken(registeredAdmin);
    }
}
