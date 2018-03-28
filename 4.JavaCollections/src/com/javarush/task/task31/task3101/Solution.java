package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.*;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) {
        File path = new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);

        String parent = resultFileAbsolutePath.getParent(); // путь к файлу для записи
        String newFileString = parent + "/allFilesContent.txt"; // строка для переименования в ссотв с заданием
        File newFile = new File(newFileString);
        //        if (FileUtils.isExist(newFile)) FileUtils.deleteFile(newFile); //если есть такой фал - удаляем
        FileUtils.renameFile(resultFileAbsolutePath, newFile); // и переименовываем файл для записи
        // теперь используем переименованный resultFileAbsPath
//        System.out.println(newFileString);

        try {
            FileOutputStream outputStream = new FileOutputStream(newFile);

            List<String> fileNames = new ArrayList<>(); // might be treeset
            listDirectory(path, fileNames); // fileNames - список имен файлов для записи
            Collections.sort(fileNames); //отсортированный
            for (String fileToWrite : fileNames) {
                writeFile(path, fileToWrite, outputStream);
            }

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {}



    }

    public static void listDirectory(File dir, List<String> fileNames) {
        for (File file : dir.listFiles()) {
            System.out.println("Listing:   " + file.getName() + ", length: " + file.length());
            if (file.isDirectory()) {
                listDirectory(file, fileNames);
            } else if (file.length() <= 50) {
                fileNames.add(file.getName());
            }
        }
    }

    public static void writeFile(File dir, String fileName, FileOutputStream out) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) writeFile(file, fileName, out);
            if (file.getName().equals(fileName)) {
                try (InputStream is = new FileInputStream(file)) {
                    //перезапись файла
                    byte[] buffer = new byte[64];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        out.write(buffer, 0, count);
                    }
                    out.write('\n');
                    out.flush();
                } catch (Exception e) {}
            }
        }
    }

}
