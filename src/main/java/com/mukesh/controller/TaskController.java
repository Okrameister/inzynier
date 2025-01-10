package com.mukesh.controller;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.mukesh.repository.GroupRepository;
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

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping
    public Task createTask(@RequestHeader("Authorization") String jwt, @RequestBody TaskRequest taskRequest) throws Exception {
        AppUser user = userService.findUserByJwt(jwt);

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCreator(user);
        task.setGroupId(taskRequest.getGroupId());

        return taskService.createTask(task);
    }

    @GetMapping("/group/{groupId}")
    public List<Task> getAllTasks(@PathVariable Integer groupId) {
        // 1. Lista ID wszystkich potomnych grup (plus ta wywołana):
        List<Integer> descendantGroupIds = new ArrayList<>();

        // 2. Pobieramy z bazy grupę "startową"
        Group startGroup = groupRepository.findById(Long.valueOf(groupId))
                .orElse(null);
        if (startGroup != null) {
            // 3. Dodajemy najpierw grupę wywołaną
            descendantGroupIds.add(groupId);

            // 4. Przechodzimy wszystkie dzieci (rekurencyjnie lub iteracyjnie)
            collectAllChildGroupIds(startGroup, descendantGroupIds);
        }

        // 5. Pobieramy wszystkie taski (jak dotychczas)
        List<Task> allTasks = taskService.getAllTasks();

        // 6. Filtrowanie: bierzemy tylko te zadania, których groupId = jedna z "descendantGroupIds"
        List<Task> finalTasks = allTasks.stream()
                .filter(t -> descendantGroupIds.contains(t.getGroupId()))
                .collect(Collectors.toList());

        return finalTasks;
    }

    // Metoda rekurencyjna zbierająca ID wszystkich podgrup:
    private void collectAllChildGroupIds(Group parent, List<Integer> collector) {
        if (parent.getSubGroups() == null) {
            return;
        }
        for (Group child : parent.getSubGroups()) {
            // Dodajemy ID dziecka
            collector.add(child.getId().intValue());
            // ...i rekurencyjnie schodzimy niżej
            collectAllChildGroupIds(child, collector);
        }
    }


    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) throws Exception {
        return taskService.getTaskById(taskId);
    }
}
