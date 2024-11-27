package me.artemiyulyanov.taskmanager.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import me.artemiyulyanov.taskmanager.models.Priority;
import me.artemiyulyanov.taskmanager.models.Status;
import me.artemiyulyanov.taskmanager.repositories.PriorityRepository;
import me.artemiyulyanov.taskmanager.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TaskPriorityService {
    @Autowired
    private PriorityRepository priorityRepository;

    @PostConstruct
    public void init() {
        if (priorityRepository.count() > 0) return;

        Priority priority1 = Priority.builder()
                .id(1L)
                .name("Низкий")
                .build();

        Priority priority2 = Priority.builder()
                .id(2L)
                .name("Средний")
                .build();

        Priority priority3 = Priority.builder()
                .id(3L)
                .name("Высокий")
                .build();

        priorityRepository.save(priority1);
        priorityRepository.save(priority2);
        priorityRepository.save(priority3);
    }

    public Optional<Priority> findById(Long id) {
        return priorityRepository.findById(id);
    }
}
