package com.example.server.controllers;

import com.example.server.dto.IncomingDto;
import com.example.server.dto.OutgoingDto;
import com.example.server.services.Service;
import com.example.server.services.exceptions.IncorrectSalaryException;
import com.example.server.services.exceptions.IncorrectYearException;
import com.example.server.utils.exceptions.IncorrectMonthException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;


public class ControllerTest {

    static Service service;
    static Controller controller;

    @BeforeAll
    private static void init(){
        service = Mockito.mock(Service.class);
        controller = new Controller(service);
    }

    @Test
    public void testTheControllerWithWrongYearDto(){
        IncomingDto wrongYearDto = new IncomingDto(0, "APRIL", 1000);

        try {
            when(service.getDtoWithHourIncome(wrongYearDto)).thenThrow(new IncorrectYearException(0));
        } catch (Exception ignored) {

        }

        ResponseEntity responseEntity = controller.getHourIncome(wrongYearDto);

        assertSame(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testTheControllerWithWrongMonthDto(){
        IncomingDto wrongMonthDto = new IncomingDto(2010, "APRILe", 1000);

        try {
            when(service.getDtoWithHourIncome(wrongMonthDto)).thenThrow(new IncorrectMonthException("APRILe"));
        } catch (Exception ignored) {

        }

        ResponseEntity responseEntity = controller.getHourIncome(wrongMonthDto);

        assertSame(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testTheControllerWithWrongSalaryDto(){
        IncomingDto wrongSalaryDto = new IncomingDto(2010, "APRIL", 0);

        try {
            when(service.getDtoWithHourIncome(wrongSalaryDto)).thenThrow(new IncorrectSalaryException(0));
        } catch (Exception ignored) {

        }

        ResponseEntity responseEntity = controller.getHourIncome(wrongSalaryDto);

        assertSame(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testTheControllerWithCorrectDto(){
        IncomingDto wrongSalaryDto = new IncomingDto(2010, "APRIL", 10000);

        OutgoingDto outgoingDto = new OutgoingDto(2010, "APRIL", 10000, 56.82);

        try {
            when(service.getDtoWithHourIncome(wrongSalaryDto)).thenReturn(outgoingDto);
        } catch (Exception ignored) {

        }

        ResponseEntity responseEntity = controller.getHourIncome(wrongSalaryDto);

        assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
