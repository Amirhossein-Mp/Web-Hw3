package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String username) {
        System.out.println("Received login request for username: " + username);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(new UserLoginResponse(true, "Login successful"));
        } else {
            System.out.println("User not found: " + username);
            return ResponseEntity.status(401).body(new UserLoginResponse(false, "User not found"));
        }
    }
}

class UserLoginResponse {
    private boolean success;
    private String message;

    public UserLoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}