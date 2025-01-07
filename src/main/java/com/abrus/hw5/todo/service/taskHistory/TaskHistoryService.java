package com.abrus.hw5.todo.service.taskHistory;

import com.abrus.hw5.todo.dto.taskHistory.TaskHistoryResponseDto;

import java.util.List;

public interface TaskHistoryService {
    public List<TaskHistoryResponseDto> findByTodoId(Long todoId);
}
