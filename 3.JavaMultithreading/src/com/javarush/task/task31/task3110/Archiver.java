package com.javarush.task.task31.task3110;

import com.javarush.task.task31.task3110.command.Command;
import com.javarush.task.task31.task3110.command.ExitCommand;
import com.javarush.task.task31.task3110.exception.WrongZipFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Archiver {
    public static void main(String[] args) {
        Operation operation = null;

        while (true) {
            try {
                operation = askOperation();
                CommandExecutor.execute(operation);
            } catch (WrongZipFileException e) {
                ConsoleHelper.writeMessage("Вы не выбрали файл архива или выбрали неверный файл.");
            } catch (Exception e) {
                ConsoleHelper.writeMessage("Произошла ошибка. Проверьте введенные данные.");
            }

            if (operation == Operation.EXIT) break;

//            String zipPathString = null, sourcePathString = null;
//
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
//                System.out.println("Please enter the path to the archive:");
//                zipPathString = reader.readLine();
//                System.out.println("Please enter the path to the source file:");
//                sourcePathString = reader.readLine();
//            } catch (IOException e) {
//            }
//
//            Path zipPath = Paths.get(zipPathString);
//            ZipFileManager zipFileManager = new ZipFileManager(zipPath);
//            Path sourcePath = Paths.get(sourcePathString);
//
//            try {
//                zipFileManager.createZip(sourcePath);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                new ExitCommand().execute();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    public static Operation askOperation() throws IOException {
        int operationNumber = -1;
        while (true) {
            ConsoleHelper.writeMessage("Выберите операцию:");
            ConsoleHelper.writeMessage("(" + Operation.CREATE.ordinal() + ") - упаковать файлы в архив");
            ConsoleHelper.writeMessage("(" + Operation.ADD.ordinal() + ") - добавить файл в архив");
            ConsoleHelper.writeMessage("(" + Operation.REMOVE.ordinal() + ") - удалить файл из архива");
            ConsoleHelper.writeMessage("(" + Operation.EXTRACT.ordinal() + ") - распаковать архив");
            ConsoleHelper.writeMessage("(" + Operation.CONTENT.ordinal() + ") - просмотреть содержимое архива");
            ConsoleHelper.writeMessage("(" + Operation.EXIT.ordinal() + ") - выход");

            operationNumber = ConsoleHelper.readInt();

            if (operationNumber > 5 || operationNumber < 0)
                ConsoleHelper.writeMessage("Неподдрживаемая операция, попробуйте еще раз");
            else break;
        }

        return Operation.values()[operationNumber];
    }
}
