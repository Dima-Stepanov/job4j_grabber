package ru.job4j.grabber.html;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.utils.CareerHabrDateTimeParser;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 3. Мидл
 * 2. Парсинг HTML страницы. [#260358 # [#260358 #247070]]
 * CareerHabrParse парсинг сайт Хабр Карьера
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.06.2023
 */
public class CareerHabrParse implements Parse {
    private static final Logger LOG = LoggerFactory.getLogger(CareerHabrParse.class);
    private static final String JAVA = "java";
    private static final String JAVASCRIPT = "javascript";
    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);
    private final DateTimeParser dateTimeParser;

    public CareerHabrParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    /**
     * Собираем все вакансии по запросу в коллекцию
     *
     * @param link URI хабр вакансии.
     * @return List POST
     */
    @Override
    public List<Post> list(String link) {
        List<Post> postList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String linkNum = String.format("%s?%s=%d", PAGE_LINK, "page", i);
            Connection connection = Jsoup.connect(linkNum);
            try {
                Document document = connection.get();
                Elements rows = document.select(".vacancy-card__inner");
                rows.forEach(row -> {
                    Element titleElement = row.select(".vacancy-card__title").first();
                    String title = titleElement.text().toLowerCase();
                    if (title.contains(JAVA) && !title.contains(JAVASCRIPT)) {
                        String lincVacancy = titleElement.child(0).attr("href");
                        String lincVacancyPage = String.format("%s%s", SOURCE_LINK, lincVacancy);
                        Post post = detail(lincVacancyPage);
                        postList.add(post);
                    }
                });
            } catch (IOException e) {
                LOG.error("Load page: {}, error: {}", link, e);
            }
        }
        return postList;
    }

    public static void main(String[] args) {
        CareerHabrParse careerHabrParse = new CareerHabrParse(new CareerHabrDateTimeParser());
        careerHabrParse.list("");
    }

    /**
     * Получаем пост с одной ссылки на одну вакансию.
     *
     * @param link Linc one vacancy page
     * @return Post
     */
    @Override
    public Post detail(String link) {
        Connection connection = Jsoup.connect(link);
        try {
            Document document = connection.get();
            String title = document.select(".page-title__title").text();
            String dateAttr = document.select(".vacancy-header__date")
                    .select("time")
                    .attr("datetime");
            LocalDateTime dateTime = dateTimeParser.parse(dateAttr);
            String description = document.select(".vacancy-description__text").text();
            Post post = new Post(title, link, description, dateTime);
            return post;
        } catch (IOException e) {
            LOG.error("Load page: {}, error: {}", link, e);
        }
        return null;
    }
}
