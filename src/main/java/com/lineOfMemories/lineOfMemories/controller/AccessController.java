package com.lineOfMemories.lineOfMemories.controller;

import com.lineOfMemories.lineOfMemories.admin.Admin;
import com.lineOfMemories.lineOfMemories.admin.AdminDTO;
import com.lineOfMemories.lineOfMemories.admin.AdminService;
import com.lineOfMemories.lineOfMemories.admin.LoginRequest;
import com.lineOfMemories.lineOfMemories.user.User;
import com.lineOfMemories.lineOfMemories.user.UserDTO;
import com.lineOfMemories.lineOfMemories.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
public class AccessController {
    private final AdminService adminService;
    private final UserService userService;

    public AccessController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @PostMapping("/admin-register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        AdminDTO adminDTO = adminService.createAdmin(admin);

        return ResponseEntity.ok(adminDTO);
    }

    @PostMapping("/user-register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        UserDTO userDTO = userService.createUser(user);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/user-login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String jwtToken = userService.userAuthorization(loginRequest);

        return ResponseEntity.ok("Login successful: " + jwtToken);
    }

    @PostMapping("/admin-login")
    public ResponseEntity<String> loginAdmin(@RequestBody LoginRequest loginRequest) {
        String jwtToken = adminService.adminAuthorization(loginRequest);

        return ResponseEntity.ok("Login successful: " + jwtToken);
    }
}
