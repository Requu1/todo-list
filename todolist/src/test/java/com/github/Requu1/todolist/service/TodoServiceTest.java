package com.github.Requu1.todolist.service;

import com.github.Requu1.todolist.exception.DuplicateTaskException;
import com.github.Requu1.todolist.exception.ResourceNotFoundException;
import com.github.Requu1.todolist.model.Task;
import com.github.Requu1.todolist.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    private TodoRepository repository;

    @InjectMocks
    private TodoService service;

    @Test
    void shouldReturnAllTasks() {
        // given
        Task task1 = new Task();
        Task task2 = new Task();
        given(repository.findAll()).willReturn(List.of(task1, task2));

        // when
        List<Task> tasks = service.getAllTasks();

        // then
        assertEquals(2, tasks.size());
        then(repository).should(times(1)).findAll();
    }

    @Test
    void shouldSaveTaskWhenTitleIsUnique() {
        // given
        Task task = new Task();
        task.setTitle("Groceries");
        given(repository.existsByTitle("Groceries")).willReturn(false);

        // when
        service.saveTask(task);

        // then
        then(repository).should(times(1)).save(task);
    }

    @Test
    void shouldThrowExceptionWhenTaskAlreadyExists() {
        // given
        Task task = new Task();
        task.setTitle("Duplicate");
        given(repository.existsByTitle("Duplicate")).willReturn(true);

        // when & then
        DuplicateTaskException exception = assertThrows(
                DuplicateTaskException.class,
                () -> service.saveTask(task)
        );
        assertEquals("Task with this name already exists!", exception.getMessage());
        then(repository).should(never()).save(any(Task.class));
    }

    @Test
    void shouldDeleteTask() {
        // given
        UUID id = UUID.randomUUID();

        // when
        service.deleteTask(id);

        // then
        then(repository).should(times(1)).deleteById(id);
    }

    @Test
    void shouldReturnTaskWhenIdExists() {
        // given
        UUID id = UUID.randomUUID();
        Task expectedTask = new Task();
        given(repository.findById(id)).willReturn(Optional.of(expectedTask));

        // when
        Task foundTask = service.getTaskById(id);

        // then
        assertNotNull(foundTask);
        assertEquals(expectedTask, foundTask);
        then(repository).should(times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // given
        UUID id = UUID.randomUUID();
        given(repository.findById(id)).willReturn(Optional.empty());

        // when & then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getTaskById(id)
        );
        assertTrue(exception.getMessage().contains(id.toString()));
    }

    @Test
    void shouldToggleTaskCompletionAndSave() {
        // given
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setCompleted(false);
        given(repository.findById(id)).willReturn(Optional.of(task));

        // when
        service.toggleTaskCompletion(id);

        // then
        assertTrue(task.isCompleted());
        then(repository).should(times(1)).save(task);
    }
}