package com.example.server.utils.exceptions;

public class IncorrectMonthException extends Exception{
    public IncorrectMonthException(String month){
        super(new Throwable("Month: " + month + " doesn't exist"));
    }
}
