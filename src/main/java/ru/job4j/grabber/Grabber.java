package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.html.CareerHabrParse;
import ru.job4j.grabber.html.Parse;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.store.PsqlStore;
import ru.job4j.grabber.store.Store;
import ru.job4j.grabber.utils.CareerHabrDateTimeParser;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий.
 * 6. Grabber. [#289477]
 * Задание:1. Реализовать класс Grabber.
 * Он должен выполнять все действия из технического задания.
 * 7. Web.[#289478]
 * Задание:1. Доработайте проект grabber. Для отображения результата на localhost:9000
 *
 * @author Dima_Nout
 * @since 09.01.2022
 */
public class Grabber implements Grab {
    private static final Logger LOG = LoggerFactory.getLogger(Grabber.class);
    private final Properties config = new Properties();

    /**
     * Создаёт и возвращает объект Store.
     *
     * @return Store
     */
    public Store store() {
        return new PsqlStore(config);
    }

    /**
     * Создает и возвращает планировщик quartz Scheduler.
     *
     * @return Scheduler
     * @throws SchedulerException exception
     */
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    /**
     * Загружает параметры из файла.
     *
     * @throws IOException exception.
     */
    public void config() throws IOException {
        try (InputStream in = Grabber.class
                .getClassLoader().getResourceAsStream("app.properties")) {
            this.config.load(in);
        }
    }

    /**
     * Собирает и запускает планировщик.
     *
     * @param parse     Parse.
     * @param store     Store.
     * @param scheduler Scheduler.
     * @throws SchedulerException exception.
     */
    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(config.getProperty("time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * Задача для Scheduler.
     */
    public static class GrabJob implements Job {
        private static final String LINK = "https://career.habr.com/vacancies/java_developer";
        private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd:MMM:yy, HH:mm");

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("parse");
            parse.list(LINK).forEach(store::save);
            LOG.debug("parse CareerHabrRu {}",
                    LocalDateTime.now()
                            .truncatedTo(ChronoUnit.SECONDS)
                            .format(TIME_FORMAT));
        }
    }

    /**
     * Отображение результата по адресу <a href="http://localhost:9000/">...</a>
     *
     * @param store Store.
     */
    public void web(Store store) {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(
                    Integer.parseInt(config.getProperty("port")))) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (OutputStream out = socket.getOutputStream()) {
                        out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                        for (Post post : store.getAll()) {
                            out.write(post.toString().getBytes(Charset.forName("Windows-1251")));
                            out.write(System.lineSeparator().getBytes());
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                LOG.error("WEB socket error: {}", e);
            }
        }).start();
    }

    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        grab.config();
        Scheduler scheduler = grab.scheduler();
        Store store = grab.store();
        DateTimeParser dateTimeParser = new CareerHabrDateTimeParser();
        grab.init(new CareerHabrParse(dateTimeParser), store, scheduler);
        grab.web(store);
    }
}
