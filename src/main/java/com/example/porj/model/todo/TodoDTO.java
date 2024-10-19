package com.example.porj.model.todo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Use Document instead of @Entity bcz mongoDB is a document base db
/**
 * SOMETIMES THIS TYPE OF ERROR COULD OCCUR DUE TO THE NOT SPECIFYING CORRECTLY THE @DOCUMENT(COLLECTION =
 * org.springframework.data.mongodb.UncategorizedMongoDbException:
 * Query failed with error code 2 and error message 'Field 'locale' is invalid in: { locale: \"todos\" }
 * */
@Document(collection = "todos")
public class TodoDTO {

    @Id
    private String id;

    //NotNull which is provided by spring-starter-validation
    @NotNull(message="todo cannot be null")
    private String todo;

    private String description;

    private Boolean completed;

    private Date createdAt;

    private Date updatedAt;

}
