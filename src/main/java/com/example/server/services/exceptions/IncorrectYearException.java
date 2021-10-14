package com.example.server.services.exceptions;

public class IncorrectYearException extends Exception{
    public IncorrectYearException(int date){
        super(new Throwable("Incorrect year: " + date));
    }
}
