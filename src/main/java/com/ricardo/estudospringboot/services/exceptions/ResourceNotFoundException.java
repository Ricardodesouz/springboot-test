package com.ricardo.estudospringboot.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Integer id){
        super("Request not found id " + id);

    }

}
