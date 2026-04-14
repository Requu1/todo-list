package com.github.Requu1.todolist.controller;

import com.github.Requu1.todolist.model.Task;
import com.github.Requu1.todolist.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return todoRepository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task newTask) {
        return todoRepository.save(newTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id) {
        todoRepository.deleteById(id);
    }
}
