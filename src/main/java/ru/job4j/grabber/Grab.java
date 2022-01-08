package ru.job4j.grabber;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import ru.job4j.grabber.html.Parse;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 3. Архитектура проекта - Агрегатор Java Вакансий [#260359]
 * Интерфейс для организации периодичности запуска.
 *
 * @author Dima_Nout
 * @since 08.01.2022
 */
public interface Grab {
    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}
