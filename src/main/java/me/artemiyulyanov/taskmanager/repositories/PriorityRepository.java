package me.artemiyulyanov.taskmanager.repositories;


import jakarta.transaction.Transactional;
import me.artemiyulyanov.taskmanager.models.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
