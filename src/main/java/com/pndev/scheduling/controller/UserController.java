package com.pndev.scheduling.controller;

import com.pndev.scheduling.dto.user.LoginResponseDTO;
import com.pndev.scheduling.dto.user.UserLoginDTO;
import com.pndev.scheduling.dto.user.UserRegisterDTO;
import com.pndev.scheduling.dto.user.UserResponseDTO;
import com.pndev.scheduling.model.User;
import com.pndev.scheduling.service.JwtService;
import com.pndev.scheduling.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegisterDTO dto) {
        User user = userService.createUser(dto);
        return new ResponseEntity<>(UserResponseDTO.fromEntity(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO dto) {
        User user = userService.authenticate(dto.getEmail(), dto.getPassword());
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new LoginResponseDTO(
                token,
                UserResponseDTO.fromEntity(user)
        ));
    }


    @GetMapping("/email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@Valid @RequestParam String email){
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
