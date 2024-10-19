package com.example.porj.util.exception;

public class TodoCollectionException extends Exception{
    public TodoCollectionException(String exception) {
        super(exception);
    }

    public static String noTodosFoundException(){
        return "No Available Todos Found";
    }
    public static String noTodoFoundException(String id){
        return "Todo with Id : " + id + ", Not Found";
    }

    public static String todoAlreadyExists(){
        return "Todo with given Name Already Found";
    }
}
