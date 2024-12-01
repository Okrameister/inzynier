package com.mukesh.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mukesh.models.Task;
import com.mukesh.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId) throws Exception {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Task not found"));
    }

    // Dodatkowe metody, jeśli potrzebujesz
}
