package com.mukesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mukesh.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Możesz dodać dodatkowe metody wyszukiwania, jeśli potrzebujesz
}
