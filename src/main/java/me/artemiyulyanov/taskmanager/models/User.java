package me.artemiyulyanov.taskmanager.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(unique = true)
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<Task> authorTasks;

    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<Task> executorTasks;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<TaskComment> comments;
}