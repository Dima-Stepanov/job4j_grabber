package ru.job4j.grabber.store;

import ru.job4j.grabber.model.Post;

import java.util.List;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 3. Архитектура проекта - Агрегатор Java Вакансий [#260359]
 * Интерфейс для сохранения результатов поиска.
 *
 * @author Dima_Nout
 * @since 08.01.2022
 */
public interface Store {
    /**
     * Сохраняет объявления
     *
     * @param post Post
     */
    void save(Post post);

    /**
     * Извлекает все объявления.
     *
     * @return List
     */
    List<Post> getAll();

    /**
     * Поиск объявления по ID
     *
     * @param id ште
     * @return Post.
     */
    Post findById(int id);
}
