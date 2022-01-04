package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 2.3.6. Проект. Агрегатор Java вакансий
 * 2. Парсинг HTML страницы. [#260358]
 *
 * @author Dima_Nout
 * @since 05.01.2022.
 */
public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element parent = td.parent();
            System.out.println(parent.child(1).child(0).attr("href"));
            System.out.println(parent.child(1).child(0).text());
            System.out.println(parent.child(5).text());
        }
    }
}
