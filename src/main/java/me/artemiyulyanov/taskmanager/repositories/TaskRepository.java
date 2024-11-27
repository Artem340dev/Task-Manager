package me.artemiyulyanov.taskmanager.repositories;

import me.artemiyulyanov.taskmanager.models.Priority;
import me.artemiyulyanov.taskmanager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
