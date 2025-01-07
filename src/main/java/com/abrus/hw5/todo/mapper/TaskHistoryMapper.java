package com.abrus.hw5.todo.mapper;

import com.abrus.hw5.todo.config.MapperConfig;
import com.abrus.hw5.todo.dto.taskHistory.TaskHistoryResponseDto;
import com.abrus.hw5.todo.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface TaskHistoryMapper {
    @Mapping(target="todoId", source = "todo.id")
    TaskHistoryResponseDto toDto(TaskHistory taskHistory);
}
