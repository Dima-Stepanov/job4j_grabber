package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий.
 * 1. Quartz [#175122]
 * Задание: Доработайте программу AlertRabbit.
 * 1. Нужно создать файл rabbit.properties.
 * 2. При запуске программы нужно читать файл rabbit.properties.
 * @author Dima_Nout
 * @since 02.01.2022
 */
public class AlertRabbit {
    public static void main(String[] args) {
        int interval = getInterval("rabbit.properties");
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    /**
     * Читать файл *.properties.
     *
     * @param fileProperties file resources/*.properties.
     * @return int.
     */
    private static int getInterval(String fileProperties) {
        int interval = -1;
        try (InputStream in = AlertRabbit.class.getClassLoader()
                .getResourceAsStream(fileProperties)) {
            Properties config = new Properties();
            config.load(in);
            interval = Integer.parseInt(config.getProperty("rabbit.interval"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return interval;
    }

    /**
     * Вложенный класс Rabbit.
     * Выводит на консоль текст.
     */
    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}
