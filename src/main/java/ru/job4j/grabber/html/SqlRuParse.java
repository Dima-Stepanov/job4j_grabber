package ru.job4j.grabber.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 2. Парсинг HTML страницы. [#260358]
 * 2.1.1. Парсинг https://www.sql.ru/forum/job-offers/3 [#285210]
 * Задание: 1. Доработайте метод main из предыдущего задания. Парсить нужно первые 5 страниц.
 * 2.3. Загрузка деталей поста. [#285212]
 * Задание: 1. Создайте метод для загрузки деталей объявления.
 * 2.4. SqlRuParse [#285213]
 * Задание.1. Реализуйте класс SqlRuParse.
 *
 * @author Dima_Nout
 * @since 05.01.2022.
 */
public class SqlRuParse implements Parse {
    private static final String JAVA = "java";
    private static final String JAVASCRIPT = "javascript";
    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    /**
     * Загружает список всех постов.
     *
     * @param link Ссылка на страницу с постами.
     * @return List.
     */
    @Override
    public List<Post> list(String link) {
        List<Post> postList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String sqlRuPage = String.format("%s%s", link, i);
            try {
                Document doc = Jsoup.connect(sqlRuPage).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    String hrefLink = td.child(0).attr("href");
                    String title = td.child(0).ownText().toLowerCase();
                    if (title.contains(JAVA) && !title.contains(JAVASCRIPT)) {
                        postList.add(
                                detail(hrefLink));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return postList;
    }

    /**
     * Загружает все детали одного поста,
     * (имя, описание, дату обновления, дату создания, ссылки на пост).
     *
     * @param link Link
     * @return Post
     */
    @Override
    public Post detail(String link) {
        Post post = null;
        try {
            Document doc = Jsoup.connect(link).get();
            post = new Post(
                    doc.select(".messageHeader").get(0).ownText(),
                    link,
                    doc.select(".msgBody").get(1).text(),
                    dateTimeParser.parse(
                            doc.select(".msgFooter").get(0).text().split("\s\\[")[0])
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }
}
