package me.artemiyulyanov.taskmanager.repositories;

import jakarta.transaction.Transactional;
import me.artemiyulyanov.taskmanager.models.Status;
import me.artemiyulyanov.taskmanager.models.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
