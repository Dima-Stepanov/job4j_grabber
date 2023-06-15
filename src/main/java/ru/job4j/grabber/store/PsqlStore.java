package ru.job4j.grabber.store;

import ru.job4j.grabber.model.Post;

import java.sql.*;
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
    private Connection connect;

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
            statement.setString(1, post.getTitle());
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
                    result.add(getPost(resultSet));
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
                    post = getPost(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    /**
     * Метод возвращает модель Post из SQL запроса.
     *
     * @param resultSet ResultSet
     * @return Post
     * @throws SQLException exception.
     */
    private Post getPost(ResultSet resultSet) throws SQLException {
        return new Post(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("link"),
                resultSet.getString("text"),
                resultSet.getTimestamp("created").toLocalDateTime());
    }

    /**
     * Класс закрывает Connection.
     *
     * @throws Exception exception.
     */
    @Override
    public void close() throws Exception {
        if (connect != null) {
            connect.close();
        }
    }
}
