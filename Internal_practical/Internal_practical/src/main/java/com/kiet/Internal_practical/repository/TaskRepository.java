package com.kiet.Internal_practical.repository;

import com.kiet.Internal_practical.entity.Task; // Import your Task entity

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository
import org.springframework.stereotype.Repository; // Recommended for clarity

@Repository // This annotation is optional but good practice to indicate it's a Spring repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Spring Data JPA will automatically generate the implementation for this method
    // based on its name. It finds tasks where the 'completed' field is false.
    List<Task> findByCompletedFalse();
}