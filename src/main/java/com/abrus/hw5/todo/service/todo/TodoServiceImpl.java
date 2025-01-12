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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
        try {
            todo = todoRepository.save(todo);
        } catch (DataIntegrityViolationException ex) {
            throw new com.abrus.hw5.todo.exception.DataIntegrityViolationException("Todo", ex.getCause().getCause().getMessage());
        }
        return todoMapper.toDto(todo);
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
        try {
            /*
            If an exception occurs and the transaction is rolled back, Spring will silently suppress the original exception
            and instead throw an UnexpectedRollbackException. This can cause the actual exception message to be lost, leading to confusion when trying to debug or inform the user.
            That is because the exception happens during the commit. If you want to trigger constraint checks before the commit
            then do a saveAndFlush (assuming you are extending JpaRepository for your repositories) or directly call flush on the EntityManager.
            That will trigger state sync with the database before the commit and will get you another DataAccessException
            */
            updatedTodo = todoRepository.saveAndFlush(updatedTodo);
        } catch (DataIntegrityViolationException ex) {
            throw new com.abrus.hw5.todo.exception.DataIntegrityViolationException("Todo", ex.getCause().getCause().getMessage());
        }
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTodo(updatedTodo);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            taskHistory.setOldState(objectMapper.writeValueAsString(todo));
            taskHistory.setNewState(objectMapper.writeValueAsString(updatedTodo));
        } catch(Throwable ex) {
            throw new com.abrus.hw5.todo.exception.JsonProcessingException("Error while transforming Todo object to a text column as json string: " + ex.getMessage());
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
        return todoRepository.findById(id)
                .map(todoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Todo", id));
    }
}
