package com.example.porj.repository.todo;

import com.example.porj.model.todo.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//MongoRepository<T,ID> -- T : The Object type here TodoDTO ; ID : The datatype of id in TodoDTO here String
public interface TodoRepository extends MongoRepository<TodoDTO,String> {

    @Query("{'todo':?0}") //Query parameter todo_ with 0th index 1st parameter in the method (String todo_)
    Optional<TodoDTO> findByTodo(String todo);

}
