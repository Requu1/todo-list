package com.github.Requu1.todolist.controller;

import com.github.Requu1.todolist.exception.DuplicateTaskException;
import com.github.Requu1.todolist.model.Task;
import com.github.Requu1.todolist.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;

@Controller
public class TodoWebController {
    private final TodoService service;

    public TodoWebController(TodoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", service.getAllTasks());
        model.addAttribute("newTask", new Task());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute("newTask") Task newTask, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tasks", service.getAllTasks());
            return "index";
        }

        try {
            service.saveTask(newTask);
        } catch (DuplicateTaskException e) {
            bindingResult.rejectValue("title", "error.task", e.getMessage());
            model.addAttribute("tasks", service.getAllTasks());
            return "index";
        }

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable UUID id) {
        service.deleteTask(id);
        return "redirect:/";
    }

    @PostMapping("/toggle/{id}")
    public String toggleTaskCompletion(@PathVariable UUID id) {
        service.toggleTaskCompletion(id);
        return "redirect:/";
    }
}