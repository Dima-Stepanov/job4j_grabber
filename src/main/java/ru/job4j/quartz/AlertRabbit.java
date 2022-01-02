package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * 1.1. Job c параметрами [#260360]
 * Задание: 1. Доработайте класс AlertRabbit. Добавьте в файл rabbit.properties настройки для базы данных.
 * 2. Создайте sql schema с таблицей rabbit и полем created_date.
 * 3. При старте приложения создайте connect к базе и передайте его в Job.
 * 4. В Job сделайте запись в таблицу, когда выполнена Job.
 *
 * @author Dima_Nout
 * @since 02.01.2022
 */
public class AlertRabbit {
    public static void main(String[] args) {
        Properties properties = getProperties("rabbit.properties");
        int interval = Integer.parseInt(properties.getProperty("rabbit.interval"));
        try (Connection connect = getConnection(properties)) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connect", connect);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    /**
     * Создает Properties
     *
     * @param fileProperties file resources/*.properties.
     * @return int.
     */
    private static Properties getProperties(String fileProperties) {
        Properties properties = new Properties();
        try (InputStream in = AlertRabbit.class.getClassLoader()
                .getResourceAsStream(fileProperties)) {
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Метод создает Connection.
     *
     * @param properties Properties
     * @return Connection.
     * @throws Exception ClassNotFoundException, SQLException
     */
    private static Connection getConnection(Properties properties) throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("driver"));
        return DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("login"),
                properties.getProperty("password")
        );
    }

    /**
     * Вложенный класс Rabbit.
     * Выводит на консоль текст.
     */
    public static class Rabbit implements Job {
        private LocalDateTime create = LocalDateTime.now().withNano(0);
        private final DateTimeFormatter
                timeFormatter = DateTimeFormatter
                .ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");

        public Rabbit() {
            System.out.println("Rabbit create: " + timeFormatter.format(create));
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection connect = (Connection) context
                    .getJobDetail()
                    .getJobDataMap()
                    .get("connect");
            try (PreparedStatement statement =
                         connect.prepareStatement(
                                 "insert into rabbit(create_date) values(?)")) {
                statement.setTimestamp(1, Timestamp.valueOf(create));
                statement.execute();
                System.out.println("Save create_date to table rabbit");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
