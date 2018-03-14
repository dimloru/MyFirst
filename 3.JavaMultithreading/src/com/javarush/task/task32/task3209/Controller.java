package com.javarush.task.task32.task3209;

import javax.swing.text.html.HTMLDocument;
import java.io.File;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
        View myView = new View();
        Controller controller = new Controller(myView);
        myView.setController(controller);
        myView.init();
        controller.init();

    }

    public void init() {

    }

    public void exit() {
        System.exit(0);
    }


}
