package com.gourmet.perfume.exception;

import java.util.function.Supplier;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public static CustomException categoryNotFound(String id) {
        return new CustomException("Given id : " + id + " not found :");
    }

    public static CustomException categoryNameNotFound(String name) {
        return new CustomException("Given category name : " + name + " not found");
    }

    public static CustomException categoryNameIsAlreadyExist(String name) {
        return new CustomException("Given category name is already exist : " + name.toLowerCase());
    }

    public static CustomException categoryGenderIsAlreadySameWithInputGender() {
        return new CustomException("Category gender is already same with input gender");
    }

    public static CustomException categoryIsAlreadyDeleted(String id) {
        return new CustomException("Category id : "+ id+ " is already deleted");
    }

    public static CustomException perfumeNotFound(String id) {
        return new CustomException("Given id : " +id + " does not exist");
    }

    public static CustomException perfumeNameNotFound(String name) {
        return new CustomException("Given name : " + name+ " not found");
    }

    public static CustomException noPerfumeBelongingThisBrand(String brandName) {
        return new CustomException("There is no perfume belonging to this brand : " +brandName);
    }

    public static CustomException perfumeNotFoundBetweenTheseYears(int from, int to) {
        return new CustomException("There was no perfume found between these years from : " + from + " to : " + to);
    }

    public static CustomException perfumeNameIsAlreadyExist(String name) {
        return new CustomException("Perfume name : " + name+ " is already exist");
    }

    public static CustomException perfumeAlreadyExistOnCategory(String categoryId) {
        return new CustomException("Perfume has already exist on category id : " + categoryId);
    }
}
