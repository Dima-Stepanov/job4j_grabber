package ru.job4j.grabber.store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.grabber.model.Post;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий.
 * 5. PsqlStore [#285209].
 * test.
 *
 * @author Dima_Nout
 * @since 09.01.2022
 */
public class PsqlStoreTest {
    static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = PsqlStoreTest.class
                .getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("login"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from post")) {
            statement.execute();
        }
    }

    @Test
    public void whenSavePostAndFindByGeneratedIdThenMustBeTheSame() {
        PsqlStore psqlStore = new PsqlStore(connection);
        Post post = new Post("Разработчик Java", "jobJava.ru",
                "Работа мечты разработчик Java",
                LocalDateTime.of(2022, 1, 9, 16, 42));
        psqlStore.save(post);
        assertThat(psqlStore.findById(post.getId()), is(post));
    }

    @Test
    public void whenSaveTwoPostAndFindByGeneratedIdThenMustByTheSum() {
        PsqlStore psqlStore = new PsqlStore(connection);
        Post post = new Post("Разработчик Java", "jobJava.ru",
                "Работа мечты разработчик Java",
                LocalDateTime.of(2022, 1, 9, 16, 42));
        Post post1 = new Post("Обучение Java", "job4j.ru",
                "Обучение на разработчик Java, от обучения до трудоустройства 6 месяцев",
                LocalDateTime.of(2022, 1, 9, 16, 43));
        psqlStore.save(post);
        psqlStore.save(post1);
        assertThat(psqlStore.findById(post1.getId()), is(post1));
    }

    @Test
    public void whenFindByAllPostThenEqualsListExpected() {
        PsqlStore psqlStore = new PsqlStore(connection);
        Post post = new Post("Разработчик Java", "jobJava.ru",
                "Работа мечты разработчик Java",
                LocalDateTime.of(2022, 1, 9, 16, 42));
        Post post1 = new Post("Обучение Java", "job4j.ru",
                "Обучение на разработчик Java, от обучения до трудоустройства 6 месяцев",
                LocalDateTime.of(2022, 1, 9, 16, 43));
        psqlStore.save(post);
        psqlStore.save(post1);
        assertThat(psqlStore.getAll(), is(List.of(post, post1)));
    }
}