package com.example.porj.service.todo;

import com.example.porj.model.todo.TodoDTO;
import com.example.porj.util.exception.TodoCollectionException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<TodoDTO> getAllTodos() throws TodoCollectionException;

    void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException;

    TodoDTO getTodoById(String id) throws TodoCollectionException;

    TodoDTO updateTodo(TodoDTO toUpdateTodo,String id) throws ConstraintViolationException,TodoCollectionException;

    void deleteTodo(String id) throws TodoCollectionException;
}
