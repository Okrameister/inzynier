package com.mukesh.controller;

import com.mukesh.models.ScheduleItem;
import com.mukesh.service.ScheduleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin(origins = "http://localhost:3000") // Dostosuj, jeśli port jest inny
public class ScheduleItemController {
    @Autowired
    private ScheduleItemService service;

    @GetMapping("/{day}")
    public List<ScheduleItem> getScheduleForDay(@PathVariable String day) {
        return service.getScheduleForDay(day);
    }

    @PostMapping
    public ScheduleItem addScheduleItem(@RequestBody ScheduleItem item) {
        return service.saveScheduleItem(item);
    }

    @PutMapping("/{id}")
    public ScheduleItem updateScheduleItem(@PathVariable Long id, @RequestBody ScheduleItem item) {
        item.setId(id);
        return service.saveScheduleItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteScheduleItem(@PathVariable Long id) {
        service.deleteScheduleItem(id);
    }
    @GetMapping
    public List<ScheduleItem> getAllScheduleItems() {
        return service.getAllScheduleItems();
    }
}
