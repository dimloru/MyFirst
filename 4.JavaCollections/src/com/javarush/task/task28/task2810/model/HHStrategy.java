package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";
    private static final String SITE_NAME = "http://hh.ua";
//    private static final String URL_FORMAT = "http://javarush.ru/testdata/big28data.html?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            int i = 0;
            for (;;) {
                Document document = getDocument(searchString, i);
                List<Element> elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

                if (elements.size() > 0) {
                    for (Element element : elements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setSiteName(SITE_NAME);

                        Optional.ofNullable(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title"))
                                .ifPresent(el -> {
                                    vacancy.setTitle(el.size() == 0 ? "" : el.get(0).text());
                                    vacancy.setUrl(el.size() == 0 ? "" : el.attr("href"));
                                });
                        Optional.ofNullable(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address"))
                                .ifPresent(el -> vacancy.setCity(el.size() == 0 ? "" : el.get(0).text()));
                        Optional.ofNullable(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer"))
                                .ifPresent(
                                        el -> {
                                            vacancy.setCompanyName(el.size() == 0 ? "" : el.get(0).text());
//                                            vacancy.setSiteName(el.size() == 0 ? "" : el.get(0).attr("href"));
                                        });
                        Optional.ofNullable(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation"))
                                .ifPresent(el -> vacancy.setSalary(el.size() == 0 ? "" : el.get(0).text()));

                        vacancies.add(vacancy);
                    }
                    i++;
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        final String userAgent = "Chrome/72.0.3626.109";
        final String referrer = "";
        String url = String.format(URL_FORMAT, searchString, page);

        Document doc = Jsoup.connect(url).userAgent(userAgent).referrer(referrer).get();
        return doc;
    }
}
