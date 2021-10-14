package com.example.server.dto;

import lombok.Value;

@Value
public class IncomingDto {
    int year;
    String month;
    double salary;
}
