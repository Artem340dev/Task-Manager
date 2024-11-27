package me.artemiyulyanov.taskmanager.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @Getter
    @Setter
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    @Getter
    @Setter
    private User executor;

    @Getter
    @Setter
    private String heading;

    @Getter
    @Setter
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @Getter
    @Setter
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    @Getter
    @Setter
    private Priority priority;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<TaskComment> comments;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Getter
    @Setter
    private LocalDateTime timestamp;
}