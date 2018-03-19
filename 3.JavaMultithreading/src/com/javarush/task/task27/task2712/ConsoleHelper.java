package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        String dishString;
        List<Dish> dishes = new ArrayList<>();

        writeMessage("Доступные блюда:");
        writeMessage(Dish.allDishesToString());

        while (true) {
            writeMessage("Введите название блюда, которое желаете заказать:");
            dishString = readString();
            if (dishString == null) continue;
            if (dishString.equals("exit")) break;

            Dish dish;
            try {
                dish = Dish.valueOf(dishString);
                dishes.add(dish);
            } catch (IllegalArgumentException | NullPointerException e) {  //Not null, checked earlier
                writeMessage("Извините, это блюдо не представлено в меню.\n");
            }
        }

        return dishes;
    }//- просит пользователя выбрать блюдо и добавляет его в список.
}
