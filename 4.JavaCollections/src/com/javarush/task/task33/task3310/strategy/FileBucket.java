package com.javarush.task.task33.task3310.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;
    private File file;

    public FileBucket() {
        try {
            path = Files.createTempFile("_javaRush",".tmp"); // pref suf null
            Files.deleteIfExists(path);
            Files.createFile(path);
            file = path.toFile();
//            System.out.println(path.toString() + " created");
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getFileSize() {
        long size = 0;
        try {
            size = Files.size(path);
        } catch (IOException e) {e.printStackTrace();}
        return size;
    }

    public void putEntry(Entry entry) {
        try (
                OutputStream fos = Files.newOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            System.out.println("Writing to file " + file);
            oos.writeObject(entry);
//            oos.flush(); //
        } catch (IOException e) { e.printStackTrace(); }
    }

    public Entry getEntry() {
        Entry entry = null;
        if (getFileSize() > 0) {
            try (
                    InputStream fis = Files.newInputStream(path);
                    ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                entry = (Entry)ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return entry;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
