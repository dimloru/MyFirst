package com.javarush.task.task31.task3110;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    private Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        try (
            OutputStream fileOutputStream = Files.newOutputStream(zipFile);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            InputStream fileInputStream = Files.newInputStream(source);
        ) {
            String zipEntryName = source.getFileName().toString();
            ZipEntry zipEntry = new ZipEntry(zipEntryName);
            zipOutputStream.putNextEntry(zipEntry);

            while (fileInputStream.available() > 0){
                byte[] buffer = new byte[1024];
                int bytesCount = fileInputStream.read(buffer);
                zipOutputStream.write(buffer, 0, bytesCount);
            }

            zipOutputStream.closeEntry();

        } catch (Exception e) {}


    }
}
