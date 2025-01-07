package com.abrus.hw5.todo.repository.todo;

import com.abrus.hw5.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
