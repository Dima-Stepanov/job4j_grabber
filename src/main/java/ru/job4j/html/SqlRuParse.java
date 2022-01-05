package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 2. Парсинг HTML страницы. [#260358]
 * 2.1.1. Парсинг https://www.sql.ru/forum/job-offers/3 [#285210]
 * Задание: 1. Доработайте метод main из предыдущего задания. Парсить нужно первые 5 страниц.
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
                System.out.println(parent.child(1).child(0).attr("href"));
                System.out.println(parent.child(1).child(0).text());
                System.out.println(parent.child(5).text());
            }
        }
    }
}
