package com.javarush.task.task36.task3601;

public class View {
    private Controller controller = new Controller();

//    public void setController(Controller controller) {
//        this.controller = controller;
//    }

    public void fireEventShowData() {
        System.out.println(controller.onDataListShow());
    }
}
