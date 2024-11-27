package me.artemiyulyanov.taskmanager.controllers;

import jakarta.servlet.http.HttpServletRequest;
import me.artemiyulyanov.taskmanager.models.Priority;
import me.artemiyulyanov.taskmanager.models.Status;
import me.artemiyulyanov.taskmanager.models.Task;
import me.artemiyulyanov.taskmanager.models.User;
import me.artemiyulyanov.taskmanager.repositories.TaskRepository;
import me.artemiyulyanov.taskmanager.jwt.JWTTokenService;
import me.artemiyulyanov.taskmanager.services.TaskPriorityService;
import me.artemiyulyanov.taskmanager.services.TaskStatusService;
import me.artemiyulyanov.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class AdminController {
    public static final String ERROR_TEMPLATE = "{\"error\": \"%s\"}";
    public static final String MESSAGE_TEMPLATE = "{\"message\": \"%s\"}";

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private TaskPriorityService taskPriorityService;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/api/admin/create-task")
    public ResponseEntity<String> createTask(@RequestParam String heading, @RequestParam String description, @RequestParam Long statusId, @RequestParam Long priorityId, @RequestParam(required = false) Long executorId, HttpServletRequest request) {
        String username = jwtTokenService.extractUsername(request);

        Optional<User> author = userService.findByUsername(username);
        Optional<Status> status = taskStatusService.findById(statusId);
        Optional<Priority> priority = taskPriorityService.findById(priorityId);

        System.out.println(statusId);
        System.out.println(status);

        if (!status.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(String.format(ERROR_TEMPLATE, "Status is not found!"));
        }

        if (!priority.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(String.format(ERROR_TEMPLATE, "Priority is not invalid!"));
        }

        Task task = Task.builder()
                .author(author.get())
                .description(description)
                .heading(heading)
                .status(status.get())
                .priority(priority.get())
                .timestamp(LocalDateTime.now())
                .build();

        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(String.format(MESSAGE_TEMPLATE, "Task has been applied successfully!"));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(HttpServletRequest request, Model model) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(String.format("{\"message\": \"%s\"}", "Hello World!"));
    }
}