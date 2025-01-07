package com.abrus.hw5.todo.service.todo;

import com.abrus.hw5.todo.dto.todo.CreateTodoRequestDto;
import com.abrus.hw5.todo.dto.todo.TodoResponseDto;
import com.abrus.hw5.todo.dto.todo.UpdateTodoRequestDto;

import java.util.Optional;

public interface TodoService {
    TodoResponseDto create(CreateTodoRequestDto createTodoRequestDto);

    TodoResponseDto update(Long id, UpdateTodoRequestDto updateTodoRequestDto);

    void  deleteById(Long id);

    TodoResponseDto findById(Long id);
}
