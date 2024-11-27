package me.artemiyulyanov.taskmanager.requests;

import lombok.*;

@AllArgsConstructor
public class LoginRequest {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String verificationCode;
}