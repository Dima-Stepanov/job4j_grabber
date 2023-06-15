package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 3. Мидл
 * 2.1. Преобразование даты [#289476 # [#289476 #247144]]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.06.2023
 */
public class CareerHabrDateTimeParser implements DateTimeParser {

    /**
     * Метод преобразовывает строку содержащую дату время в LocalDateTime
     * Строка имеет вид "ГГГГ-ММ-ДДТЧЧ:ММ:СС+ЧЧ:00"
     * Что соответствует стандарту
     * ISO_OFFSET_DATE_TIME	Date Time with Offset	'2011-12-03T10:15:30+01:00'
     *
     * @param parse String
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime parse(String parse) {
        return LocalDateTime.parse(parse, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
