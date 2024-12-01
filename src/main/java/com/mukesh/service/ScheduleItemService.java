package com.mukesh.service;

import com.mukesh.models.ScheduleItem;
import com.mukesh.repository.ScheduleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleItemService {
    @Autowired
    private ScheduleItemRepository repository;

    public List<ScheduleItem> getScheduleForDay(String dayOfWeek) {
        return repository.findByDayOfWeek(dayOfWeek);
    }

    public ScheduleItem saveScheduleItem(ScheduleItem item) {
        return repository.save(item);
    }

    public Optional<ScheduleItem> getScheduleItemById(Long id) {
        return repository.findById(id);
    }

    public void deleteScheduleItem(Long id) {
        repository.deleteById(id);
    }

    public List<ScheduleItem> getAllScheduleItems() {
        return repository.findAll();
    }

}
