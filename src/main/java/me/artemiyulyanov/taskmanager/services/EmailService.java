package me.artemiyulyanov.taskmanager.services;

import jakarta.transaction.Transactional;
import me.artemiyulyanov.taskmanager.models.EmailVerificationCode;
import me.artemiyulyanov.taskmanager.repositories.EmailVerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class EmailService {
    private static final long EXPIRY_TIME_MS = 3000;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailVerificationCodeRepository emailVerificationCodeRepository;

    public boolean validateCode(String email, String code) {
        Optional<EmailVerificationCode> emailVerificationCode = emailVerificationCodeRepository.findByEmail(email);

        if (emailVerificationCode.isPresent() && emailVerificationCode.get().getCode().equals(code) && emailVerificationCode.get().getExpiryDate().isAfter(LocalDateTime.now())) return true;
        return false;
    }

    public void authUser(String email) {
        Optional<EmailVerificationCode> emailVerificationCode = emailVerificationCodeRepository.findByEmail(email);

        if (emailVerificationCode.isPresent()) {
            emailVerificationCodeRepository.delete(emailVerificationCode.get());
        }
    }

    public void sendVerificationCodeTo(String email) {
        if (emailVerificationCodeRepository.existsByEmail(email)) emailVerificationCodeRepository.deleteByEmail(email);

        String code = Integer.toString((int) (899999 * Math.random()) + 100000);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification code");
        message.setText(String.format("Hi! Your verification code is: %s. Enter it to get authenticated", code));
        mailSender.send(message);

        EmailVerificationCode emailVerificationCode = EmailVerificationCode.builder()
                .email(email)
                .code(code)
                .expiryDate(LocalDateTime.now().plusSeconds(EXPIRY_TIME_MS))
                .build();

        emailVerificationCodeRepository.save(emailVerificationCode);
    }
}