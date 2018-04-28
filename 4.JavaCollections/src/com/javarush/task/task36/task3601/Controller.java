package com.javarush.task.task36.task3601;

import java.util.List;

public class Controller {
    private Model model = new Model();
//    private View view;

//    public void setModel(Model model) {
//        this.model = model;
//    }
//
//    public void setView(View view) {
//        this.view = view;
//    }

    public List<String> onDataListShow() {
        return model.getStringDataList();
    }
}
