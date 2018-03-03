package com.javarush.task.task31.task3110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Archiver {
    public static void main(String[] args) {
        String zipPathString = null, sourcePathString = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please enter the path to the archive:");
            zipPathString = reader.readLine();
            System.out.println("Please enter the path to the source file:");
            sourcePathString = reader.readLine();
        } catch(IOException e) {}

        Path zipPath = Paths.get(zipPathString);
        ZipFileManager zipFileManager = new ZipFileManager(zipPath);
        Path sourcePath = Paths.get(sourcePathString);

        try {
            zipFileManager.createZip(sourcePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
