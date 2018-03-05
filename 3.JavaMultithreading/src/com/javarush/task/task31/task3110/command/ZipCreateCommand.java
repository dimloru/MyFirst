package com.javarush.task.task31.task3110.command;

import com.javarush.task.task31.task3110.ConsoleHelper;
import com.javarush.task.task31.task3110.ZipFileManager;
import com.javarush.task.task31.task3110.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.javarush.task.task31.task3110.ConsoleHelper.readString;

public class ZipCreateCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Создание архива.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Введите полное имя файла или директории для архивации");
            String sourcePathString = ConsoleHelper.readString();
            Path sourcePath = Paths.get(sourcePathString);
            zipFileManager.createZip(sourcePath);
            ConsoleHelper.writeMessage("Архив создан.");
        } catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }


    }
}
