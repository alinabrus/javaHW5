package com.abrus.hw5.todo.dto.todo;

import com.abrus.hw5.todo.model.Priority;
import com.abrus.hw5.todo.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
    private Status status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long userId;
}
