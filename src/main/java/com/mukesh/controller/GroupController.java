package com.mukesh.controller;

import com.mukesh.models.AppUser;
import com.mukesh.models.Group;
import com.mukesh.repository.GroupRepository;
import com.mukesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{groupId}/subgroups")
    public List<Group> getSubGroups(@RequestHeader("Authorization") String jwt, @PathVariable Long groupId) {
        AppUser appUser = userService.findUserByJwt(jwt);
        List<Integer> userGroups = appUser.getGroups();
        List<Group> subGroups = groupRepository.findByParentGroupId(groupId);

        subGroups.removeIf(group -> !userGroups.contains(group.getId().intValue()));

        return subGroups;
    }


    @GetMapping("/{groupId}/path")
    public List<Group> getGroupPath(@RequestHeader("Authorization") String jwt, @PathVariable Long groupId) {
        AppUser appUser = userService.findUserByJwt(jwt);
        List<Integer> userGroups = appUser.getGroups();
        List<Group> path = new ArrayList<>();

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group current = groupOptional.get();
            while (current != null) {
                if (userGroups.contains(current.getId().intValue())) {
                    path.add(0, current); // Dodajemy na początek listy, aby zachować hierarchię
                }
                current = current.getParentGroup();
            }
        }
        return path;
    }


    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupRepository.save(group);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group groupDetails) {
        return groupRepository.findById(id).map(group -> {
            group.setName(groupDetails.getName());
            group.setParentGroup(groupDetails.getParentGroup());
            return ResponseEntity.ok(groupRepository.save(group));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroup(@PathVariable Long id) {
        return groupRepository.findById(id).map(group -> {
            groupRepository.delete(group);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
