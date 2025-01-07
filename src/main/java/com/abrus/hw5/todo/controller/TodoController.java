package com.abrus.hw5.todo.controller;

import com.abrus.hw5.todo.dto.todo.CreateTodoRequestDto;
import com.abrus.hw5.todo.dto.todo.TodoResponseDto;
import com.abrus.hw5.todo.dto.todo.UpdateTodoRequestDto;
import com.abrus.hw5.todo.service.todo.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/todo")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public TodoResponseDto create(@RequestBody @Valid CreateTodoRequestDto createTodoRequestDto) {
        return todoService.create(createTodoRequestDto);
    }

    @PutMapping("/{id}")
    public TodoResponseDto update(@PathVariable Long id, @RequestBody @Valid UpdateTodoRequestDto updateTodoRequestDto) {
        return todoService.update(id, updateTodoRequestDto);
    }

    @GetMapping("/{id}")
    public TodoResponseDto findById(@PathVariable Long id) {
        return todoService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        todoService.deleteById(id);
    }
}
