package com.abrus.hw5.todo.mapper;

import com.abrus.hw5.todo.config.MapperConfig;
import com.abrus.hw5.todo.dto.todo.CreateTodoRequestDto;
import com.abrus.hw5.todo.dto.todo.TodoResponseDto;
import com.abrus.hw5.todo.dto.todo.UpdateTodoRequestDto;
import com.abrus.hw5.todo.model.Todo;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TodoMapper {
    Todo toModel(CreateTodoRequestDto createTodoRequestDto);

    Todo toModel(UpdateTodoRequestDto updateTodoRequestDto);

    TodoResponseDto toDto(Todo todo);
}
