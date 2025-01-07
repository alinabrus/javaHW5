package com.abrus.hw5.todo.dto.todo;

import com.abrus.hw5.todo.model.Priority;
import com.abrus.hw5.todo.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record UpdateTodoRequestDto(
        @NotEmpty(message = "Task title cannot be empty")
        @Length(min = 3, max = 100)
        String title,
        @Length(max = 500)
        String description,
        @NotNull(message = "Task priority cannot be empty")
        Priority priority,
        @NotNull(message = "Task status cannot be empty")
        Status status,
        @NotNull(message = "Task due date cannot be empty")
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime dueDate
) {
}
