package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private int minSize = -333;
    private int maxSize = -333;
    private List<Path> foundFiles = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length
        boolean needAddFile = false;
        boolean stateChanged = false;

        if (minSize != -333) {
            if (content.length > minSize) { needAddFile = stateChanged ? needAddFile : true; }
            else { needAddFile = false; }
            stateChanged = true;
        }
        if (maxSize != -333) {
            if (content.length < maxSize) { needAddFile = stateChanged ? needAddFile : true; }
            else { needAddFile = false; }
            stateChanged = true;
        }
        if (partOfName != null) {
            if (file.getFileName().toString().contains(partOfName)) {
                needAddFile = stateChanged ? needAddFile : true;
            } else {
                needAddFile = false;
            }
            stateChanged = true;
        }
        if (partOfContent != null) {
            String str = new String(content);
            if (str.contains(partOfContent)) {
                needAddFile = stateChanged ? needAddFile : true;
            } else {
                needAddFile = false;
            }
            stateChanged = true;
        }

        if (needAddFile) {
            foundFiles.add(file);
//            String str = new String(content);
//            System.out.println(str);
        }

        return super.visitFile(file, attrs);
    }


    public List<Path> getFoundFiles() {

        return foundFiles;
    }

    public void setPartOfName(String partOfName) {

        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {

        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {

        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {

        this.maxSize = maxSize;
    }
}
