package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Files.createFile;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt", Paths.get("D:/MyDownloads"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        // implement this method
        URL url = new URL(urlString);
        String fileName = urlString.substring(urlString.lastIndexOf("/") + 1); // чистое имя
        Path fileNamePath = Paths.get(fileName);

        Path tempFile = Files.createTempFile("javarush-temp", ".txt");

        InputStream inputStream = url.openStream();
        Files.copy(inputStream, tempFile);  //StandardCopyOption.REPLACE_EXISTING

        Path destinationFile = downloadDirectory.resolve(fileNamePath);
        Files.move(tempFile, destinationFile);

        return destinationFile;
    }
}
