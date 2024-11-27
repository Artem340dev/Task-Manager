package me.artemiyulyanov.taskmanager.jwt;

import jakarta.servlet.http.HttpServletRequest;
import me.artemiyulyanov.taskmanager.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class JWTTokenService {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, String> blacklistedTokens;

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }

    public String extractUsername(HttpServletRequest request) {
        String token = extractToken(request);

        if (token != null && jwtUtil.validateToken(token)) {
            return jwtUtil.extractUsername(token);
        }

        return null;
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.hasKey(token);
    }

    public void logout(String token) {
        blacklistedTokens.opsForValue().set(token, "blacklisted", Duration.ofHours(1));
    }
}