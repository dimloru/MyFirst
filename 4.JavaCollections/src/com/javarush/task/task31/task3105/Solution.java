package com.javarush.task.task31.task3105;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        Path tempZipFile = Files.createTempFile(null, null);
        Path fileToAdd = Paths.get(args[0]);
        Path zipFile = Paths.get(args[1]);
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile));
             ZipOutputStream zipOutputStream = new ZipOutputStream((Files.newOutputStream(tempZipFile))) ) {
            ZipEntry zipEntry;
            boolean replaced = false;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                zipOutputStream.putNextEntry(new ZipEntry(zipEntry.getName()));
//                System.out.println(zipEntry.getName());
                String zipEntryFileName = zipEntry.getName().contains("/") ?
                        zipEntry.getName().substring(zipEntry.getName().lastIndexOf("/") + 1) :
                        zipEntry.getName(); // некорректно работает с вложенными папками

                if (!zipEntryFileName.equals(fileToAdd.getFileName().toString())) {
                    copyData(zipInputStream, zipOutputStream);
                } else {
                    Files.copy(fileToAdd, zipOutputStream);
                    replaced = true;
                }
                zipInputStream.closeEntry();
                zipOutputStream.closeEntry();
            }

            if (!replaced) {
                zipOutputStream.putNextEntry(new ZipEntry("new" + args[0].substring(args[0].indexOf("/"))));
                Files.copy(fileToAdd, zipOutputStream);
                zipOutputStream.closeEntry();
            }
        }
        Files.copy(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void copyData(InputStream in, OutputStream out) {
        byte[] buffer = new byte[8 * 1024];
        int len;
        try {
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
