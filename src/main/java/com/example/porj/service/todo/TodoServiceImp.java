package com.example.porj.service.todo;

import com.example.porj.model.todo.TodoDTO;
import com.example.porj.repository.todo.TodoRepository;
import com.example.porj.util.exception.TodoCollectionException;
import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImp implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImp(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoDTO> getAllTodos() throws TodoCollectionException {
        try {
            List<TodoDTO> allTodos = todoRepository.findAll();
            if (!allTodos.isEmpty()) {
                return allTodos;
            } else {
                throw new TodoCollectionException(TodoCollectionException.noTodosFoundException());
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while fetching Todos from database", e);
        }
    }

    @Override
    @Transactional
    public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> availableTodo = todoRepository.findByTodo(todoDTO.getTodo());
        if (availableTodo.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.todoAlreadyExists());
        } else {
            try {
                todoDTO.setCreatedAt(new Date());
                todoDTO.setUpdatedAt(new Date());
                todoRepository.save(todoDTO);
            } catch (MongoException e) {
                throw new RuntimeException("Error while creating Todo", e);
            }
        }
    }

    @Override
    public TodoDTO getTodoById(String id) throws TodoCollectionException {
        try {
            Optional<TodoDTO> todoById = todoRepository.findById(id);
            if (todoById.isPresent()) {
                return todoById.get();
            } else {
                throw new TodoCollectionException(TodoCollectionException.noTodoFoundException(id));
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while fetching Todo by Id from database", e);
        }
    }

    @Override
    @Transactional
    public TodoDTO updateTodo(TodoDTO toUpdateTodo, String initialId) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoById = todoRepository.findById(initialId); //100 td1
        Optional<TodoDTO> todoDTO = todoRepository.findByTodo(toUpdateTodo.getTodo()); //200 td1 //100 td2
        if (todoById.isPresent()) {
            TodoDTO initialTodo = todoById.get();
            if (todoDTO.isPresent() && !todoDTO.get().getId().equals(initialId)) {
                throw new TodoCollectionException(TodoCollectionException.todoAlreadyExists());
            }
            try {
                initialTodo.setTodo(toUpdateTodo.getTodo() != null ? toUpdateTodo.getTodo() : initialTodo.getTodo());
                initialTodo.setDescription(toUpdateTodo.getDescription() != null ? toUpdateTodo.getDescription() : initialTodo.getDescription());
                initialTodo.setCompleted(toUpdateTodo.getCompleted() != null ? toUpdateTodo.getCompleted() : initialTodo.getCompleted());
                initialTodo.setUpdatedAt(new Date());
                return todoRepository.save(initialTodo);
            } catch (MongoException e) {
                throw new RuntimeException("Error while updating Todo", e);
            }

        } else {
            throw new TodoCollectionException(TodoCollectionException.noTodoFoundException(initialId));
        }


    }

    @Override
    public void deleteTodo(String id) throws TodoCollectionException {
        try {
            Optional<TodoDTO> byId = todoRepository.findById(id);
            if(byId.isPresent()){
                todoRepository.delete(byId.get());
            }else{
                throw new TodoCollectionException(TodoCollectionException.noTodoFoundException(id));
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while deleting Todo", e);
        }
    }
}
