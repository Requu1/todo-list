package com.github.Requu1.todolist.repository;

import com.github.Requu1.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Task, UUID> {
    boolean existsByTitle(String title);
}
