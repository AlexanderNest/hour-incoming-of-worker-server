package com.example.server.services.exceptions;

public class IncorrectSalaryException extends Exception{
    public IncorrectSalaryException(double salary){
        super(new Throwable("Incorrect salary: " + salary));
    }
}
