package com.mukesh.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mukesh.models.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findByTaskId(Long taskId);
}
