package ru.job4j.grabber.store;

import ru.job4j.grabber.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 3. Архитектура проекта - Агрегатор Java Вакансий [#260359]
 * Класс сохраняет результаты поиска в память.
 *
 * @author Dima_Nout
 * @since 08.01.2022
 */
public class MemStore implements Store {
    private List<Post> postList = new ArrayList<>();
    private int id = 0;

    @Override
    public void save(Post post) {
        if (!this.postList.contains(post)) {
            post.setId(id++);
            this.postList.add(post);
        }
    }

    @Override
    public List<Post> getAll() {
        return this.postList;
    }

    @Override
    public Post findById(int id) {
        return postList.stream()
                .filter(p -> p.getId() == id)
                .findFirst().orElse(null);
    }
}
