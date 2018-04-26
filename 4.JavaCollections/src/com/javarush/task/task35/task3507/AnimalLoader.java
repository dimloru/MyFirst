package com.javarush.task.task35.task3507;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AnimalLoader extends ClassLoader {
    private Path parentPath;
    private String pack;

    public AnimalLoader(String path, String pack) {
        super();
        this.parentPath = Paths.get(path);
        this.pack = pack;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        Path filePath = Paths.get(name + ".class");
        Path fullPath = parentPath.resolve(filePath);

        byte[] buffer = null;
        try {
            buffer = Files.readAllBytes(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(pack + "data." + name, buffer, 0, buffer.length);
    }
}
