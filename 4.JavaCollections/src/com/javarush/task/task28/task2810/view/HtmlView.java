package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replace(".", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        System.out.println(vacancies.size());
        updateFile(getUpdatedFileContent(vacancies));
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies){
        Document document = null;

        try {
            document = getDocument();

            List<Element> templateClassElements = document.getElementsByClass("template");
            Element templateElement = null, templateElementCopy = null;
            if (templateClassElements.size() > 0) {
                templateElement = templateClassElements.get(0);
                templateElementCopy = templateElement.clone();

                templateElementCopy.removeClass("template");
                templateElementCopy.removeAttr("style");
            }

            List<Element> existingVacancies = document.getElementsByClass("vacancy");
            for (Element el : existingVacancies) {
                if (!el.hasClass("template")) {
                    el.remove();
                }
            }

            for (Vacancy vacancy : vacancies) {
                Element workingTemplate = templateElementCopy.clone();
                for (Element el : workingTemplate.getElementsByClass("city")) {
                    el.text(vacancy.getCity());
                }
                for (Element el : workingTemplate.getElementsByClass("companyName")) {
                    el.text(vacancy.getCompanyName());
                }
                for (Element el : workingTemplate.getElementsByClass("salary")) {
                    el.text(vacancy.getSalary());
                }
                for (Element el : workingTemplate.getElementsByClass("title")) {
                    for (Element innerEl : el.getElementsByAttribute("href")) {
                        innerEl.text(vacancy.getTitle());
                        innerEl.attr("href", vacancy.getUrl());
                    }
                }

                templateElement.before(workingTemplate.outerHtml());
            }

        } catch (Exception e) { //IO, NPE
            e.printStackTrace();
            return "Some exception occurred";
        }

        return document.html();
    }

    private void updateFile(String str) {
        try (PrintStream out = new PrintStream(new FileOutputStream(filePath))) {
            out.print(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument() throws IOException {
        File inputFile = new File(filePath);
        Document document = Jsoup.parse(inputFile,"UTF-8", "");

        return document;
    }
}
