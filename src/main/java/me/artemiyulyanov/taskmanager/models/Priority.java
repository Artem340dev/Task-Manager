package me.artemiyulyanov.taskmanager.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "priorities")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String name;

    @OneToMany(mappedBy = "priority", fetch = FetchType.LAZY)
    private Set<Task> tasks;
}