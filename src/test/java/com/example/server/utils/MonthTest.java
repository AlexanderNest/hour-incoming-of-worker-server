package com.example.server.utils;

import com.example.server.utils.exceptions.IncorrectMonthException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MonthTest {

    @Test
    void janToNum(){
        String numeric = "";

        try {
            numeric = Month.getNumeric("JANUARY");
        } catch (IncorrectMonthException ignored) {

        }

        assertEquals(numeric, "01");
    }

    @Test
    void incorrectMonth(){
        try {
            Month.getNumeric("JANUARYy");
        } catch (IncorrectMonthException ignored) {
            assert true;
            return;
        }
        assert false;
    }

    @Test
    void withLeadingZero(){
        int num = 1;

        String res = Month.withLeadingZero(num);

        assertEquals(res, "01");
    }

    @Test
    void withLeadingZero2(){
        int num = 10;

        String res = Month.withLeadingZero(num);

        assertEquals(res, "10");
    }
}
