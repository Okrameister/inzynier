package com.mukesh.repository;

import com.mukesh.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByOrderByCreatedAtDesc();
}
