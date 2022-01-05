package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 2.1. Преобразование даты [#289476]
 * Задание.
 * 1. Реализовать метод, преобразующий дату из формата sql.ru.
 * Основной класс.
 *
 * @author Dima_Nout
 * @since 05.01.2021.
 */
public class SqlRuDateTimeParser implements DateTimeParser {
    private static final DateTimeFormatter NOW_YESTERDAY = DateTimeFormatter.ofPattern("d M yy");
    private static final DateTimeFormatter FORMAT_PARSE = DateTimeFormatter.ofPattern("d M yy HH:mm");
    private static final int SIZE_DATE = 4;
    private static final int SIZE_DATE_NOW_YESTERDAY = 2;
    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("вчера", LocalDate.now().minusDays(1).format(NOW_YESTERDAY)),
            Map.entry("сегодня", LocalDate.now().format(NOW_YESTERDAY)),
            Map.entry("янв", "1"), Map.entry("фев", "2"), Map.entry("мар", "3"),
            Map.entry("апр", "4"), Map.entry("май", "5"), Map.entry("июн", "6"),
            Map.entry("июл", "7"), Map.entry("авг", "8"), Map.entry("сен", "9"),
            Map.entry("окт", "10"), Map.entry("ноя", "11"), Map.entry("дек", "12"));

    /**
     * Преобразует строку с датой с сайта SQL.RU в LocalDataTime
     * String[].length==4 data-> data[0]=число, data[1]=месяц, data[2]=год, data[3]=минуты:часы.
     * String[].length==2 data-> data[0]=сегодня/вчера, data[1]=минуты:часы.
     *
     * @param parse String
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime parse(String parse) {
        String[] date = parse.split("[, ]+");
        String parseDateTime = "";
        if (date.length == SIZE_DATE) {
            parseDateTime = String.join(
                    " ", date[0], MONTHS.get(date[1]),
                    date[2], date[3]);
        }
        if (date.length == SIZE_DATE_NOW_YESTERDAY) {
            parseDateTime = String.join(
                    " ", MONTHS.get(date[0]),
                    date[1]);
        }
        return LocalDateTime.parse(parseDateTime, FORMAT_PARSE);
    }
}
