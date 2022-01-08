package ru.job4j.grabber;

import org.junit.Test;
import ru.job4j.grabber.model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 3. Архитектура проекта - Агрегатор Java Вакансий [#260359]
 * test MemStore.
 *
 * @author Dima_Nout
 * @since 08.01.2022
 */
public class MemStoreTest {
    public MemStore memStore = new MemStore();

    @Test
    public void whenSavePost() {
        Post post = new Post("java", "https://java.com",
                "работа мечты Java разработчик",
                LocalDateTime.of(2022, 1, 8, 23, 12));
        memStore.save(post);
        assertThat(memStore.findById(0), is(post));
    }

    @Test
    public void whenSaveTwoPost() {
        Post post = new Post("java", "https://java.com",
                "работа мечты Java разработчик",
                LocalDateTime.of(2022, 1, 8, 23, 12));
        Post post1 = new Post(
                "java", "https://job4j.ru",
                "Учеба мечты Job4j",
                LocalDateTime.of(2021, 8, 29, 13, 0));
        memStore.save(post);
        memStore.save(post1);
        assertThat(memStore.findById(1), is(post1));
    }

    @Test
    public void whenSaveTwoEqualsPost() {
        Post post = new Post("java", "https://java.com",
                "работа мечты Java разработчик",
                LocalDateTime.of(2022, 1, 8, 23, 12));
        memStore.save(post);
        memStore.save(post);
        assertThat(memStore.findById(0), is(post));
        assertNull(memStore.findById(1));
    }

    @Test
    public void whenFindAllPost() {
        Post post = new Post("java", "https://java.com",
                "работа мечты Java разработчик",
                LocalDateTime.of(2022, 1, 8, 23, 12));
        Post post1 = new Post(
                "java", "https://job4j.ru",
                "Учеба мечты Job4j",
                LocalDateTime.of(2021, 8, 29, 13, 0));
        memStore.save(post);
        memStore.save(post1);
        assertThat(memStore.getAll(), is(List.of(post, post1)));
    }
}