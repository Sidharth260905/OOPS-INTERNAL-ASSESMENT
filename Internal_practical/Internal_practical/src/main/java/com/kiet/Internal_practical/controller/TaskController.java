package com.kiet.Internal_practical.controller;

import com.kiet.Internal_practical.entity.Task;
import com.kiet.Internal_practical.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Use @Controller for serving views
import org.springframework.ui.Model; // To pass data to the view
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // For form binding
import org.springframework.web.bind.annotation.PathVariable; // For path variables like ID
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // For flash attributes

import java.time.LocalDate; // Ensure LocalDate is imported
import java.util.List; // Ensure List is imported
import java.util.Optional; // Ensure Optional is imported

@Controller // This is now a regular Spring MVC Controller to serve views
@RequestMapping("/tasks") // Map to the /tasks base path for all methods
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Handles GET requests to /tasks (e.g., http://localhost:8080/tasks)
    // This will display the list of tasks and the form to add new tasks.
    @GetMapping
    public String listTasks(Model model) {
        System.out.println("DEBUG: GET /tasks request received. Calling listTasks().");
        List<Task> tasks = taskRepository.findAll();
        System.out.println("DEBUG: Found " + tasks.size() + " tasks in the database.");
        tasks.forEach(task -> System.out.println("  - Task: " + task.getTitle() + " (Completed: " + task.isCompleted() + ")"));

        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    // Handles POST requests from the form to add a new task
    @PostMapping("/add") // Map to /tasks/add for form submission
    public String addTask(@ModelAttribute("newTask") Task task, RedirectAttributes redirectAttributes) {
        System.out.println("DEBUG: POST /tasks/add request received. Calling addTask().");
        System.out.println("DEBUG: Incoming Task - Title: '" + task.getTitle() + "', Description: '" + task.getDescription() + "', DueDate: '" + task.getDueDate() + "'");

        // Set default values if not provided by the form (e.g., completed status)
        if (task.getDueDate() == null) {
            task.setDueDate(LocalDate.now().plusDays(7)); // Default due date 7 days from now
            System.out.println("DEBUG: DueDate was null, set to default: " + task.getDueDate());
        }
        task.setCompleted(false); // New tasks are typically incomplete
        System.out.println("DEBUG: Task will be saved as: Title='" + task.getTitle() + "', DueDate='" + task.getDueDate() + "', Completed=" + task.isCompleted());

        // Save the new task to the database
        Task savedTask = taskRepository.save(task);
        System.out.println("DEBUG: Task saved successfully with ID: " + savedTask.getId());

        // Add a success message to be displayed after redirect
        redirectAttributes.addFlashAttribute("message", "Task added successfully!");
        System.out.println("DEBUG: Redirecting to /tasks");
        // Redirect back to the main task list page at /tasks
        return "redirect:/tasks";
    }

    // Handles POST requests to toggle a task's completed status
    // Mapped to /tasks/toggle/{id}
    @PostMapping("/toggle/{id}")
    public String toggleTaskCompleted(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("DEBUG: POST /tasks/toggle/" + id + " request received. Calling toggleTaskCompleted().");

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            // Toggle the completed status
            task.setCompleted(!task.isCompleted());
            taskRepository.save(task);
            System.out.println("DEBUG: Task ID " + id + " toggled to completed=" + task.isCompleted());
            redirectAttributes.addFlashAttribute("message", "Task status updated!");
        } else {
            System.out.println("DEBUG: Task ID " + id + " not found for toggling.");
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found!");
        }
        // Redirect back to the main task list page
        return "redirect:/tasks";
    }

    // NEW: Handles POST requests to delete a task
    // Mapped to /tasks/delete/{id}
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("DEBUG: POST /tasks/delete/" + id + " request received. Calling deleteTask().");

        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            System.out.println("DEBUG: Task ID " + id + " deleted successfully.");
            redirectAttributes.addFlashAttribute("message", "Task deleted successfully!");
        } else {
            System.out.println("DEBUG: Task ID " + id + " not found for deletion.");
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found for deletion!");
        }
        // Redirect back to the main task list page
        return "redirect:/tasks";
    }
}
