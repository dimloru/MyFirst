package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoikrugStrategy implements Strategy {
//    https://moikrug.ru/vacancies?q=java+Dnepropetrovsk
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
    private static final String SITE_NAME = "https://moikrug.ru";
//    private static final String URL_FORMAT = "http://javarush.ru/testdata/big28data2.html?q=java+%s&page=%d";

//    public static void main(String[] args) throws IOException {
//        MoikrugStrategy strategy = new MoikrugStrategy();
//        strategy.getVacancies("junior");
//        Document document = strategy.getDocument("junior", 0);
//        System.out.println(document);
//        List<Vacancy> vacancies = strategy.getVacancies("Москва");
//        System.out.println(vacancies);
//
//    }

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();

        try {
            int i = 0;
            for (;;) {
                Document document = getDocument(searchString, i);
                List<Element> elements = document.getElementsByClass("job");

                if (elements.size() > 0) {
                    for (Element element : elements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setSiteName(SITE_NAME);

                        Optional.ofNullable(element.getElementsByClass("title"))
                                .ifPresent(el -> {
                                    if (el.size() > 0) {
                                        vacancy.setTitle(el.get(0).text());
                                        Optional.ofNullable(el.get(0).select("a").first()).ifPresent(
                                                aEl -> vacancy.setUrl(SITE_NAME + aEl.attr("href")));
                                    }
                                });
                        Optional.ofNullable(element.getElementsByClass("company_name"))
                                .ifPresent(el -> vacancy.setCompanyName(el.size() == 0 ? "" : el.get(0).text()));
                        Optional.ofNullable(element.getElementsByClass("location"))
                                .ifPresent(el -> vacancy.setCity(el.size() == 0 ? "" : el.get(0).text()));

                        Optional.ofNullable(element.getElementsByClass("salary"))
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
