package me.artemiyulyanov.taskmanager.controllers;

import me.artemiyulyanov.taskmanager.jwt.JWTTokenService;
import me.artemiyulyanov.taskmanager.models.User;
import me.artemiyulyanov.taskmanager.requests.LoginRequest;
import me.artemiyulyanov.taskmanager.services.UserService;
import me.artemiyulyanov.taskmanager.services.EmailService;
import me.artemiyulyanov.taskmanager.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {
    public static final String ERROR_TEMPLATE = "{\"error\": \"%s\"}";
    public static final String MESSAGE_TEMPLATE = "{\"message\": \"%s\"}";

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private JWTTokenService jwtTokenService;

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestParam String type, @RequestBody LoginRequest loginRequest, Model model) {
        if (type.equals("email")) {
            String email = loginRequest.getEmail();

            if (email == null || !userService.userExistsByEmail(loginRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(String.format(ERROR_TEMPLATE, "The user does not exist!"));
            }

            emailService.sendVerificationCodeTo(email);
            return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(String.format(ERROR_TEMPLATE, "The 6-digit verification code has been send to your email address!"));
        }

        if (type.equals("password")) {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            if (!userService.isUserVaild(username, password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(String.format(ERROR_TEMPLATE, "User is invalid!"));
            }

            String token = jwtUtil.generateToken(username);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(String.format("{\"jwt_token\": \"%s\"}", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(String.format(ERROR_TEMPLATE, "User has not been authorized: the type of authorization is invalid!"));
    }

    @PostMapping("/auth/login/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody LoginRequest loginRequest, Model model) {
        String email = loginRequest.getEmail();
        String verificationCode = loginRequest.getVerificationCode();

        if (emailService.validateCode(email, verificationCode)) {
            Optional<User> user = userService.findByEmail(email);
            emailService.authUser(email);

            String token = jwtUtil.generateToken(user.get().getUsername());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(String.format("{\"jwt_token\": \"%s\"}", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(String.format(ERROR_TEMPLATE, "User has not been authorized: User is invalid!"));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader, Model model) {
        String token = authorizationHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(String.format(ERROR_TEMPLATE, "Token is invalid!"));
        }

        jwtTokenService.logout(token);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(String.format(MESSAGE_TEMPLATE, "The logout has been performed successfully!"));
    }
}