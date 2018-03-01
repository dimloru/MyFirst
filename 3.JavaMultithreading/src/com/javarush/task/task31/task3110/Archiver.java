package com.javarush.task.task31.task3110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Archiver {
    public static void main(String[] args) {
        System.out.println("Please enter the path to the archive:");
        String zipPathString = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            zipPathString = reader.readLine();
        } catch(IOException e) {}
        Path zipPath = Paths.get(zipPathString);
        ZipFileManager zipFileManager = new ZipFileManager(zipPath);

        String sourcePathString = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            sourcePathString = reader.readLine();
        } catch(IOException e) {}
        Path sourcePath = Paths.get(sourcePathString);
        try {
            zipFileManager.createZip(sourcePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
