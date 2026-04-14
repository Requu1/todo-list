package com.github.Requu1.todolist.controller;

import com.github.Requu1.todolist.exception.DuplicateTaskException;
import com.github.Requu1.todolist.model.Task;
import com.github.Requu1.todolist.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(service.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task newTask) {
        try{
            service.saveTask(newTask);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
        } catch(DuplicateTaskException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleTask(@PathVariable UUID id) {
        service.toggleTaskCompletion(id);
        return ResponseEntity.ok(service.getTaskById(id));
    }
}
