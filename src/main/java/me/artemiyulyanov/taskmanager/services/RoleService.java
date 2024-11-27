package me.artemiyulyanov.taskmanager.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import me.artemiyulyanov.taskmanager.models.Role;
import me.artemiyulyanov.taskmanager.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (roleRepository.count() > 0) return;

        Role roleUser = new Role();
        roleUser.setName("USER");
        roleRepository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleRepository.save(roleAdmin);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }
}