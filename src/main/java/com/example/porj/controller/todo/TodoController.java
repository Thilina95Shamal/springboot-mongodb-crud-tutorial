package com.example.porj.controller.todo;

import com.example.porj.model.todo.TodoDTO;
import com.example.porj.service.todo.TodoService;
import com.example.porj.util.exception.TodoCollectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    //@RequestMapping(value = "/todos", method = RequestMethod.GET)
    //@GetMapping("/todos") - USE THIS INSTEAD OF REQUESTMAPPING Which reduce boilerplate code and promotes consistency
    @GetMapping("/api/v1/todos")
    public ResponseEntity<?> getAllTodos() {
        try {
            List<TodoDTO> list = todoService.getAllTodos();
            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/api/v1/createTodo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO)  {
        try{
            todoService.createTodo(todoDTO);
            return new ResponseEntity<>("Successfully Todo Created",HttpStatus.CREATED);
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    //use @PathVariable instead of @RequestParam in REST APIs
    @GetMapping("/api/v1/todo/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") String id){
        try{
            TodoDTO todoById = todoService.getTodoById(id);
            return new ResponseEntity<>(todoById,HttpStatus.OK);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/v1/updateTodo/{id}")
    public ResponseEntity<?> updateTodoById(@PathVariable("id") String id, @RequestBody TodoDTO dto){
        try{
            TodoDTO updatedTodo = todoService.updateTodo(dto,id);
            return new ResponseEntity<>(updatedTodo,HttpStatus.CREATED);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/api/v1/deleteTodo/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable("id") String id){
        try{
            todoService.deleteTodo(id);
            return new ResponseEntity<>("Successfully Todo Deleted",HttpStatus.OK);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
