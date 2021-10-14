package com.example.server.controllers;

import com.example.server.services.Service;
import com.example.server.dto.IncomingDto;
import com.example.server.dto.OutgoingDto;
import com.example.server.services.exceptions.IncorrectSalaryException;
import com.example.server.services.exceptions.IncorrectYearException;
import com.example.server.utils.exceptions.IncorrectMonthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {
    private final Service service;

    public Controller(@Autowired Service service){
        this.service = service;
    }

    @Cacheable(cacheNames = "months")
    @PostMapping(path = "hourIncome", consumes = "application/json", produces = "application/json")
    public ResponseEntity getHourIncome(@RequestBody IncomingDto incomingDto) {
        return handleGetHourIncome(incomingDto);
    }

    private ResponseEntity handleGetHourIncome(IncomingDto incomingDto){
        try{
            OutgoingDto outgoingDto = this.service.getDtoWithHourIncome(incomingDto);
            return new ResponseEntity<>(outgoingDto, HttpStatus.OK);
        }
        catch (IncorrectMonthException | IncorrectSalaryException | IncorrectYearException ex){
            return new ResponseEntity<>(toJsonMessage(ex.getCause().getMessage()), HttpStatus.BAD_REQUEST);
        } catch (InterruptedException | IOException e) {
            return new ResponseEntity<>(toJsonMessage(e.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private String toJsonMessage(String message){
        return "{ \"message\": \"" + message + "\" }";
    }
}
