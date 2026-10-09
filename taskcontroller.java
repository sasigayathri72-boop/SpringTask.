package com.example.taskflow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE UPPER(t.priority) = UPPER(:priority)")
    List<Task> findByPriority(@Param("priority") String priority);
}

@RestController
@RequestMapping("/tasks")
class TaskController {

    private final TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return repository.save(task);
    }

    @GetMapping
    public List<Task> getTasks(
            @RequestParam(required = false) String priority) {

        if (priority != null && !priority.isBlank()) {
            return repository.findByPriority(priority);
        }

        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Task not found"));
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(
            @PathVariable Long id,
            @RequestParam boolean completed) {

        Task task = getTask(id);
        task.setCompleted(completed);
        return repository.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task updatedTask) {

        Task task = getTask(id);
        task.setTitle(updatedTask.getTitle());
        task.setPriority(updatedTask.getPriority());
        task.setCompleted(updatedTask.isCompleted());

        return repository.save(task);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        Task task = getTask(id);
        repository.delete(task);
        return "Task deleted successfully";
    }
}