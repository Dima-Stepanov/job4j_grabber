package ru.job4j.grabber.utils;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * 3. Мидл
 * 2.1. Преобразование даты [#289476 # [#289476 #247144]]
 * CareerHabrDateTimeParser TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.06.2023
 */
public class CareerHabrDateTimeParserTest {
    private CareerHabrDateTimeParser dateTimeParser = new CareerHabrDateTimeParser();

    @Test
    public void whenStringData1ThenReturnLocalDateTime() {
        String data = "2023-06-14T18:27:39+03:00";
        LocalDateTime expected = LocalDateTime.of(2023, 06, 14, 18, 27, 39);
        LocalDateTime result = dateTimeParser.parse(data);
        assertThat(result, is(expected));
    }

    @Test
    public void whenStringData2ThenReturnLocalDateTime() {
        String data = "2023-06-15T10:45:30+03:00";
        LocalDateTime expected = LocalDateTime.of(2023, 06, 15, 10, 45, 30);
        LocalDateTime result = dateTimeParser.parse(data);
        assertThat(result, is(expected));
    }
}