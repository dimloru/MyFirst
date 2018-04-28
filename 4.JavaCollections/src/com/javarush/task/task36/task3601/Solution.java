package com.javarush.task.task36.task3601;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* 
MVC - простая версия
*/
public class Solution {
    public static void main(String[] args) {
        View view = new View();
//        Controller controller = new Controller();
//        Model model = new Model();

//        view.setController(controller);

//        controller.setView(view);
//        controller.setModel(model);

        view.fireEventShowData();
    }
}
