package com.github.Requu1.todolist.service;

import com.github.Requu1.todolist.exception.DuplicateTaskException;
import com.github.Requu1.todolist.exception.ResourceNotFoundException;
import com.github.Requu1.todolist.model.Task;
import com.github.Requu1.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository){
        this.repository = repository;
    }

    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    public void saveTask(Task task){
        if(repository.existsByTitle(task.getTitle())){
            throw new DuplicateTaskException("Task with this name already exists!");
        }
        repository.save(task);
    }

    public void deleteTask(UUID taskId){
        repository.deleteById(taskId);
    }

    public Task getTaskById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find a task with ID: " + id));
    }

    public void toggleTaskCompletion(UUID id) {
        Task task = getTaskById(id);
        task.setCompleted(!task.isCompleted());
        repository.save(task);
    }

}
