package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
//        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = String.format(URL_FORMAT, searchString, 0);
        final String userAgent = "Chrome/72.0.3626.109";
        final String referrer = "";


        try {
            Document doc = Jsoup.connect(url).userAgent(userAgent).referrer(referrer).get();
            System.out.println(doc.html());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
