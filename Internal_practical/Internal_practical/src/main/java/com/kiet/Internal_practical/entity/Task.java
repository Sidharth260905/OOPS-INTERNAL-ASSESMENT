package com.kiet.Internal_practical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate; // Import for dueDate

@Entity 
public class Task {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID for new tasks
    private Long id;

    private String title;
    private String description; // Added as per common task attributes
    private LocalDate dueDate; // Represents the due date of the task
    private boolean completed; // To track if the task is finished

    // Default constructor (often required by JPA)
    public Task() {
    }

    // Parameterized constructor (optional, but good for convenience)
    public Task(String title, String description, LocalDate dueDate, boolean completed) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    // --- Getters and Setters for all fields ---
    // The controller uses these methods to access and set task properties.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", dueDate=" + dueDate +
               ", completed=" + completed +
               '}';
    }
}
