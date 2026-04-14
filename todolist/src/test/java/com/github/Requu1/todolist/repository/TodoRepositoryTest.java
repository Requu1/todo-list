package com.github.Requu1.todolist.repository;

import com.github.Requu1.todolist.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class TodoRepositoryTest {
    @Autowired
    private TodoRepository repository;

    @Test
    void createdTaskWithGivenTitleExists(){
        // given
        Task task = new Task("Buy groceries");

        //when
        repository.save(task);

        //then
        assertTrue(repository.existsByTitle(task.getTitle()));
    }

    @Test
    void taskGetsDeletedFromTheRepositoryProperly(){
        // given
        Task task = new Task("Laundry");
        repository.save(task);

        // when
        repository.deleteById(task.getId());

        // then
        assertFalse(repository.existsByTitle(task.getTitle()));
    }

    @Test
    void repositoryContainsAllAddedPreviouslyTasks(){
        // given
        Task task1 = new Task("Laundry");
        Task task2 = new Task("Groceries");
        Task task3 = new Task("Lunch");

        // when
        repository.save(task1);
        repository.save(task2);
        repository.save(task3);

        // then
        List<Task> tasks = repository.findAll();
        assertEquals(3,tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertTrue(tasks.contains(task3));

    }
}