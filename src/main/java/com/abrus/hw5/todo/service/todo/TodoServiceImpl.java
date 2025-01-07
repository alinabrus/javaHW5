package com.abrus.hw5.todo.service.todo;

import com.abrus.hw5.todo.dto.todo.CreateTodoRequestDto;
import com.abrus.hw5.todo.dto.todo.TodoResponseDto;
import com.abrus.hw5.todo.dto.todo.UpdateTodoRequestDto;
import com.abrus.hw5.todo.exception.EntityNotFoundException;
import com.abrus.hw5.todo.mapper.TodoMapper;
import com.abrus.hw5.todo.model.Status;
import com.abrus.hw5.todo.model.TaskHistory;
import com.abrus.hw5.todo.model.Todo;
import com.abrus.hw5.todo.repository.taskHistory.TaskHistoryRepository;
import com.abrus.hw5.todo.repository.todo.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoMapper todoMapper;
    private final TodoRepository todoRepository;
    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public TodoResponseDto create(CreateTodoRequestDto createTodoRequestDto) {
        Todo todo = todoMapper.toModel(createTodoRequestDto);
        todo.setStatus(Status.PENDING);
        todo.setCreatedDate(LocalDateTime.now());
        return todoMapper.toDto(todoRepository.save(todo));
    }

    @Override
    @Transactional
    public TodoResponseDto update(Long id, UpdateTodoRequestDto updateTodoRequestDto) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        Todo todo = todoOptional
                .orElseThrow(() -> new EntityNotFoundException("Todo", id));

        Todo updatedTodo = todoMapper.toModel(updateTodoRequestDto);
        updatedTodo.setId(id);
        updatedTodo.setCreatedDate(todo.getCreatedDate());
        updatedTodo.setUpdatedDate(LocalDateTime.now());
        updatedTodo = todoRepository.save(updatedTodo);

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTodo(updatedTodo);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            taskHistory.setOldState(objectMapper.writeValueAsString(todo));
            taskHistory.setNewState(objectMapper.writeValueAsString(updatedTodo));
        } catch(JsonProcessingException jsonProcessingException) {
            throw new RuntimeException("Error while transforming Todo object to a text column as json string: " + jsonProcessingException.getMessage());
        }
        taskHistory.setChangeDate(updatedTodo.getUpdatedDate());
        taskHistoryRepository.save(taskHistory);

        return todoMapper.toDto(updatedTodo);
    }

    @Override
    public void deleteById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return;
        }
        throw new EntityNotFoundException("Todo", id);
    }

    @Override
    public TodoResponseDto findById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo", id));
        return todoMapper.toDto(todo);
    }
}
