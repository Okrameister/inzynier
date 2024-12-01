package com.mukesh.repository;

import com.mukesh.models.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {
    List<ScheduleItem> findByDayOfWeek(String dayOfWeek);
    List<ScheduleItem> findByDayOfWeekAndStartTime(String dayOfWeek, LocalTime startTime);
}
