package me.artemiyulyanov.taskmanager.repositories;

import me.artemiyulyanov.taskmanager.models.EmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, Long> {
    Optional<EmailVerificationCode> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
}