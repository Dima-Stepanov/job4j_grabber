package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.StringJoiner;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 2. Парсинг HTML страницы. [#260358]
 * 2.1.1. Парсинг https://www.sql.ru/forum/job-offers/3 [#285210]
 * Задание: 1. Доработайте метод main из предыдущего задания. Парсить нужно первые 5 страниц.
 * 2.3. Загрузка деталей поста. [#285212]
 * Задание: 1. Создайте метод для загрузки деталей объявления.
 *
 * @author Dima_Nout
 * @since 05.01.2022.
 */
public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            String sqlRu = String.format("https://www.sql.ru/forum/job-offers/%s", i);
            Document doc = Jsoup.connect(sqlRu).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element parent = td.parent();
                String link = parent.child(1).child(0).attr("href");
                String title = parent.child(1).child(0).text();
                String data = parent.child(5).text();
            }
        }
    }

    /**
     * Метод для загрузки деталей объявления.
     * Загружает описание объявления и дату поста.
     *
     * @param link Ссылка объявления.
     * @return String.
     */
    public static String getDescription(String link) {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        try {
            Document doc = Jsoup.connect(link).get();
            Element row = doc.selectFirst(".msgTable");
            result.add(row.child(0).child(1).child(1).text());
            result.add(row.child(0).child(2).child(0).ownText().split("\s\\[")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
