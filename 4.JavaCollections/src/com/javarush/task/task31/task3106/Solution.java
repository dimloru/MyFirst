package com.javarush.task.task31.task3106;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) {
        String resultFileName = args[0];
        String[] parts = new String[args.length - 1];
        System.arraycopy(args, 1, parts, 0, parts.length);
        Arrays.sort(parts);
        List<InputStream> inputStreams = new ArrayList<>(); // можно было решить treeset'ом

        try {
            for (String part : parts) {
                inputStreams.add(new FileInputStream(part));
            }
        } catch (FileNotFoundException e) {}

        try (ZipInputStream zipInputStream = new ZipInputStream(new SequenceInputStream(Collections.enumeration(inputStreams)));
             OutputStream out = new FileOutputStream(resultFileName) )
        {
            for (ZipEntry entry = null; (entry = zipInputStream.getNextEntry()) != null; ) {
                byte[] buffer = new byte[1024 * 8];
                int count;
                while ((count = zipInputStream.read(buffer, 0, buffer.length)) > 0) {
                    out.write(buffer, 0, count);
                }
                out.flush();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
