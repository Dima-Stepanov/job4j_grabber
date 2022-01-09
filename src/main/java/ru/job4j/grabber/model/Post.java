package ru.job4j.grabber.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий.
 * 2.2. Модель данных - Post. [#285211]
 * Задание.
 * 1. Создайте модель данных Post.
 *
 * @author Dima_Nout
 * @since 05.01.2022
 */
public class Post {
    /**
     * id типа int - идентификатор вакансии (берется из нашей базы данных);
     */
    private int id;
    /**
     * title типа String - название вакансии;
     */
    private String titel;
    /**
     * link типа String - ссылка на описание вакансии;
     */
    private String link;
    /**
     * description типа String - описание вакансии;
     */
    private String description;
    /**
     * created типа LocalDateTime - дата создания вакансии.
     */
    private LocalDateTime created;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy, HH:mm", Locale.getDefault());

    public Post(String titel, String link, String description, LocalDateTime created) {
        this.titel = titel;
        this.link = link;
        this.description = description;
        this.created = created;
    }

    public Post(int id, String titel, String link, String description, LocalDateTime created) {
        this.id = id;
        this.titel = titel;
        this.link = link;
        this.description = description;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public Post setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitel() {
        return titel;
    }

    public Post setTitel(String titel) {
        this.titel = titel;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Post setLink(String link) {
        this.link = link;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Post setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Post setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    @Override
    public String toString() {
        String sl = System.lineSeparator();
        return "id=" + id + sl + ", titel='"
                + titel + '\'' + sl + "\t, link=" + link
                + sl + ", description='" + description + '\''
                + sl + "\t" + created.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && Objects.equals(titel, post.titel)
                && Objects.equals(link, post.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titel, link);
    }
}
