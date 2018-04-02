package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/* 
Что внутри папки?
*/
public class Solution extends SimpleFileVisitor<Path> {

    public static void main(String[] args) throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String dirStr = reader.readLine();
            Path dir = Paths.get(dirStr);
            if (!Files.isDirectory(dir)) {
                System.out.println(dirStr + " - не папка");
            } else {
                Solution solution = new Solution();
                EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
                Files.walkFileTree(dir, options, 20, solution);

                // Alternatiove
//                long countFiles = Files.walk(dir).filter(Files::isRegularFile).count();

                System.out.println("Всего папок - " + solution.getFolderCount());
                System.out.println("Всего файлов - " + solution.getFileCount());
                System.out.println("Общий размер - " + solution.getTotalWeight());
            }

        } catch (IOException e) {}
    }

    public int folderCount = -1, fileCount = 0, totalWeight = 0; // folderCount == -1 потому что preVisit считает и коркевую папку
    // если не пройдет сменить на 0

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (Files.isRegularFile(file)) {
            fileCount++;
            totalWeight += Files.size(file);
        }

        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        folderCount++;
        return super.preVisitDirectory(dir, attrs);
    }

    public int getFolderCount() {
        return folderCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getTotalWeight() {
        return totalWeight;
    }
}
