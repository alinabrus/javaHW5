package com.abrus.hw5.todo.repository.taskHistory;

import com.abrus.hw5.todo.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findByTodoId(Long todoId);
}
