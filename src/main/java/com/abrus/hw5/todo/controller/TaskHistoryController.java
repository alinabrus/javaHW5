package com.abrus.hw5.todo.controller;

import com.abrus.hw5.todo.dto.taskHistory.TaskHistoryResponseDto;
import com.abrus.hw5.todo.service.taskHistory.TaskHistoryService;
import com.abrus.hw5.todo.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskHistoryController {
    private final TaskHistoryService taskHistoryService;

    @GetMapping("/todo/{todoId}/history")
    public List<TaskHistoryResponseDto> getTodoHistory(@PathVariable Long todoId) {
        return taskHistoryService.findByTodoId(todoId);
    }
}
