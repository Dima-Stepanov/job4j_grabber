package ru.job4j.grabber.html;

import ru.job4j.grabber.model.Post;

import java.util.List;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий.
 * 2.4. SqlRuParse [#285213]
 * Задание.1. Реализуйте класс SqlRuParse.
 * Интерфейс.
 *
 * @author Dima_Nout
 * @since 06.01.2022
 */
public interface Parse {
    List<Post> list(String link);

    Post detail(String link);
}
