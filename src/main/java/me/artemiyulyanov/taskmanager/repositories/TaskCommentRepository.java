package me.artemiyulyanov.taskmanager.repositories;

import me.artemiyulyanov.taskmanager.models.Task;
import me.artemiyulyanov.taskmanager.models.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
}
