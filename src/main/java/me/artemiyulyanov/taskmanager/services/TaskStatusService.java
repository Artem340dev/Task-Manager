package me.artemiyulyanov.taskmanager.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import me.artemiyulyanov.taskmanager.models.Status;
import me.artemiyulyanov.taskmanager.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TaskStatusService {
    @Autowired
    private StatusRepository statusRepository;

    @PostConstruct
    public void init() {
        if (statusRepository.count() > 0) return;

        Status status1 = Status.builder()
                .id(1L)
                .name("В ожидании")
                .build();

        Status status2 = Status.builder()
                .id(2L)
                .name("В процессе")
                .build();

        Status status3 = Status.builder()
                .id(3L)
                .name("Завершено")
                .build();

        statusRepository.save(status1);
        statusRepository.save(status2);
        statusRepository.save(status3);
    }

    public Optional<Status> findById(Long id) {
        return statusRepository.findById(id);
    }
}