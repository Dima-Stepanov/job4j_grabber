package ru.job4j.grabber.store;

import ru.job4j.grabber.model.Post;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий.
 * 5. PsqlStore [#285209].
 *
 * @author Dima_Nout
 * @since 09.01.2022
 */
public class PsqlStore implements Store, AutoCloseable {
    Connection connect;

    /**
     * Конструктор создает соединение с базой данных.
     *
     * @param config Properties.
     */
    public PsqlStore(Properties config) {
        try {
            Class.forName(config.getProperty("jdbc.driver"));
            this.connect = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("login"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Конструктор для тестов Liquibase.
     *
     * @param connect Connection.
     */
    public PsqlStore(Connection connect) {
        this.connect = connect;
    }

    /**
     * Добавляет Post в базу данных в таблицу post.
     *
     * @param post Post
     */
    @Override
    public void save(Post post) {
        try (PreparedStatement statement = connect.prepareStatement(
                "insert into post(name, link, text, created) values(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitel());
            statement.setString(2, post.getLink());
            statement.setString(3, post.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            statement.execute();
            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    post.setId(generatedKey.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает все элементы пост из базы данных.
     *
     * @return List.
     */
    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        try (PreparedStatement statement = connect.prepareStatement(
                "select * from post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post(
                            resultSet.getString("name"),
                            resultSet.getString("link"),
                            resultSet.getString("text"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    );
                    post.setId(resultSet.getInt("id"));
                    result.add(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Возвращает результат Post по ID.
     *
     * @param id Post.id
     * @return Post.
     */
    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement statement = connect.prepareStatement(
                "select * from post where id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post(
                            resultSet.getString("name"),
                            resultSet.getString("link"),
                            resultSet.getString("text"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    );
                    post.setId(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (connect != null) {
            connect.close();
        }
    }

    public static void main(String[] args) {
        Properties config = new Properties();
        try (InputStream in = PsqlStore.class
                .getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            config.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PsqlStore psqlStore = new PsqlStore(config);
        Post post = new Post("Разработчик Java", "jobJava.ru",
                "Работа мечты разработчик Java",
                LocalDateTime.of(2022, 1, 9, 16, 42));
        Post post1 = new Post("Обучение Java", "job4j.ru",
                "Обучение на разработчик Java, от обучения до трудоустройства 6 месяцев",
                LocalDateTime.of(2022, 1, 9, 16, 43));
        psqlStore.save(post);
        psqlStore.save(post1);
        System.out.println("Method findById:");
        System.out.println(psqlStore.findById(post.getId()));
        System.out.println(psqlStore.findById(post1.getId()));
        System.out.println("Method getAll:");
        List<Post> list = psqlStore.getAll();
        list.forEach(System.out::println);
    }
}
