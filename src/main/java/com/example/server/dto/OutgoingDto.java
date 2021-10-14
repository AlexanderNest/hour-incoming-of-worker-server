package com.example.server.dto;

import lombok.Value;

@Value
public class OutgoingDto {
    int year;
    String month;
    double salary;
    double hourIncome;
}
