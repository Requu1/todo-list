package com.github.Requu1.todolist.controller;

import com.github.Requu1.todolist.exception.ResourceNotFoundException;
import com.github.Requu1.todolist.model.Task;
import com.github.Requu1.todolist.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import java.util.UUID;

@Controller
public class TodoWebController {
    private final TodoRepository repository;

    public TodoWebController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", repository.findAll());
        model.addAttribute("newTask", new Task());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute("newTask") Task newTask, BindingResult bindingResult, Model model) {
        if (repository.existsByTitle(newTask.getTitle())) {
            bindingResult.rejectValue("title", "error.task", "Task with this name already exists!");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("tasks", repository.findAll());
            return "index";
        }
        repository.save(newTask);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable UUID id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/toggle/{id}")
    public String toggleTaskCompletion(@PathVariable UUID id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find a task with ID: " + id));
        task.setCompleted(!task.isCompleted());
        repository.save(task);
        return "redirect:/";
    }


}
