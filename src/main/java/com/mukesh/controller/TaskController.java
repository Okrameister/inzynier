package com.mukesh.controller;

import java.util.List;

import com.mukesh.request.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mukesh.models.*;
import com.mukesh.service.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Task createTask(@RequestHeader("Authorization") String jwt, @RequestBody TaskRequest taskRequest) throws Exception {
        AppUser user = userService.findUserByJwt(jwt);

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCreator(user);

        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) throws Exception {
        return taskService.getTaskById(taskId);
    }
}
