package com.example.server.services;

import com.example.server.dto.IncomingDto;
import com.example.server.dto.OutgoingDto;
import com.example.server.services.exceptions.IncorrectSalaryException;
import com.example.server.services.exceptions.IncorrectYearException;
import com.example.server.utils.Month;
import com.example.server.utils.exceptions.IncorrectMonthException;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;


@org.springframework.stereotype.Service
public class Service {
    private final String dayServerHost;
    private final int workingHours;

    Logger log = Logger.getLogger(Service.class.getName());

    public Service(@Value("${server.dayServer.host}")  String dayServerHost,
                   @Value("${server.workingHours}") int workingHours){
        this.dayServerHost = dayServerHost;
        this.workingHours = workingHours;
    }

    public OutgoingDto getDtoWithHourIncome(IncomingDto incomingDto) throws IncorrectMonthException, IOException,
            InterruptedException, IncorrectSalaryException, IncorrectYearException {

        log.info(""+incomingDto.hashCode());

        String numericMonth = Month.getNumeric(incomingDto.getMonth());

        double salary = incomingDto.getSalary();
        if (salary <= 0){
            throw new IncorrectSalaryException(salary);
        }

        int year = incomingDto.getYear();
        if (!(1900 <= year && year <= 2099)){
            throw new IncorrectYearException(year);
        }

        int workingDays = getWorkingDaysCount(String.valueOf(year), numericMonth);

        double hourIncome = salary / workingDays / this.workingHours;
        hourIncome = DoubleRounder.round(hourIncome, 2);


        return new OutgoingDto(year, incomingDto.getMonth(), salary, hourIncome);
    }

    private int getWorkingDaysCount(String year, String numericMonth) throws IOException, InterruptedException {
        String url = dayServerHost + year + numericMonth;

        HttpClient client = HttpClient.newHttpClient();

        int workingDays = 0;

        for (int day = 1; day <= 31; day++){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + Month.withLeadingZero(day)))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.body().equals("0")){
                workingDays++;
            }

            log.info("Day: " + day + " - " + response.body());
        }


       return workingDays;
    }

}
