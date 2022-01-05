package ru.job4j.grabber.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 2.1. Преобразование даты [#289476]
 * Задание.
 * 1. Реализовать метод, преобразующий дату из формата sql.ru.
 * Test
 *
 * @author Dima_Nout
 * @since 05.01.2021.
 */
public class SqlRuDateTimeParserTest {

    public SqlRuDateTimeParser timeParser = new SqlRuDateTimeParser();

    @Test
    public void whenParseDateSqlRuDec() {
        String dataSql = "2 дек 19, 22:29";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2019, 12, 2,
                22, 29);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuJun() {
        String dataSql = "25 июн 18, 21:56";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2018, 6, 25,
                21, 56);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuFeb() {
        String dataSql = "22 фев 16, 10:56";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2016, 2, 22,
                10, 56);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuNow() {
        String dataSql = "сегодня, 02:30";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDate.now().atTime(2, 30);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuYesterday() {
        String dataSql = "вчера, 19:23";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDate.now().atTime(19, 23);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuYesterdayTwo() {
        String dataSql = "вчера, 12:06";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDate.now().atTime(12, 6);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuMay() {
        String dataSql = "12 май 20, 08:17";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2020, 5, 12,
                8, 17);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuAug() {
        String dataSql = "12 авг 20, 00:09";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2020, 8, 12,
                0, 9);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuSep() {
        String dataSql = "11 сен 20, 14:43";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2020, 9, 11,
                14, 43);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuOkt() {
        String dataSql = "12 окт 15, 15:03";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2015, 10, 12,
                15, 3);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuNov() {
        String dataSql = "17 ноя 10, 19:33";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2010, 11, 17,
                19, 33);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuJul() {
        String dataSql = "20 июл 05, 07:47";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2005, 7, 20,
                7, 47);
        assertThat(result, is(expected));
    }

    @Test
    public void whenParseDateSqlRuApr() {
        String dataSql = "03 апр 00, 13:03";
        LocalDateTime result = timeParser.parse(dataSql);
        LocalDateTime expected = LocalDateTime.of(2000, 4, 3,
                13, 3);
        assertThat(result, is(expected));
    }
}