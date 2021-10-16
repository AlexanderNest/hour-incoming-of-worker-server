package com.example.server.integration.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    static String isDayOffUrl = "https://isdayoff.ru/";
    static HttpClient client;

    @BeforeAll
    static void init(){
        client = HttpClient.newHttpClient();
    }

    @Test
    public void workingDayTest(){
        String url = isDayOffUrl + "2021" + "10" + "15";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            assert false;
        }

        String result = response.body();

        assertEquals(result, "0");
    }

    @Test
    public void notWorkingDayTest(){
        String url = isDayOffUrl + "2021" + "10" + "16";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            assert false;
        }

        String result = response.body();

        assertEquals(result, "1");
    }

    @Test
    public void incorrectDate(){
        String url = isDayOffUrl + "2021" + "10" + "100";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            assert false;
        }

        String result = response.body();

        assertEquals(result, "100");
    }
}
