package com.abrus.hw5.todo.service.taskHistory;

import com.abrus.hw5.todo.dto.taskHistory.TaskHistoryResponseDto;
import com.abrus.hw5.todo.mapper.TaskHistoryMapper;
import com.abrus.hw5.todo.repository.taskHistory.TaskHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskHistoryServiceImpl implements TaskHistoryService {
    private final TaskHistoryRepository taskHistoryRepository;
    private final TaskHistoryMapper taskHistoryMapper;

    @Override
    public List<TaskHistoryResponseDto> findByTodoId(Long todoId) {
        return taskHistoryRepository.findByTodoId(todoId)
                .stream()
                .map(taskHistoryMapper::toDto)
                .toList();
    }
}
