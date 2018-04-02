package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        List<String> files = new ArrayList<>();
        Queue<File> folders = new LinkedBlockingQueue<>();
        folders.offer(new File(root));
        File dir = folders.poll();
        while (dir != null) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    folders.offer(file);
                } else {
                    files.add(file.getAbsolutePath());
                }
            }
            dir = folders.poll();
        }
        return files;
    }

    public static void main(String[] args) {



    }
}
