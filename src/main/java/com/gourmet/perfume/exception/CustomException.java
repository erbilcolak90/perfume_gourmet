package com.gourmet.perfume.exception;

public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }

    public static CustomException categoryNotFound(String id){
        return new CustomException("Given id : "+ id + " not found :");
    }

    public static CustomException categoryNameNotFound(String name) {
        return new CustomException("Given category name : " + name + " not found");
    }
}
