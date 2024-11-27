package me.artemiyulyanov.taskmanager.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "statuses")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String name;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private Set<Task> tasks;
}