package me.artemiyulyanov.taskmanager.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import me.artemiyulyanov.taskmanager.models.Role;
import me.artemiyulyanov.taskmanager.models.User;
import me.artemiyulyanov.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
@Transactional
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostConstruct
//    public void init() {
//        User testUser = User.builder()
//                .username("Artemiy")
//                .email("artemij.honor@gmail.com")
//                .firstName("Artemiy")
//                .lastName("Ulyanov")
//                .password(passwordEncoder.encode("HelloBro31"))
//                .roles(Set.of(roleService.findRoleByName("USER"), roleService.findRoleByName("ADMIN")))
//                .build();
//
//        userRepository.save(testUser);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isUserVaild(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) return false;

        return passwordEncoder.matches(password, user.get().getPassword());
    }
}