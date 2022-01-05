package ru.job4j.grabber.utils;

import java.time.LocalDateTime;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 2.1. Преобразование даты [#289476]
 * Задание.
 * 1. Реализовать метод, преобразующий дату из формата sql.ru.
 * Интерфейс.
 *
 * @author Dima_Nout
 * @since 05.01.2021.
 */
public interface DateTimeParser {
    LocalDateTime parse(String parse);
}
