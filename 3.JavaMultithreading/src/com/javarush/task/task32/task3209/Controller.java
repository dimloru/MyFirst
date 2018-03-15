package com.javarush.task.task32.task3209;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public static void main(String[] args) {
        View myView = new View();
        Controller controller = new Controller(myView);
        myView.setController(controller);
        myView.init();
        controller.init();

    }

    public void init() {
        createNewDocument();
    }

    public void resetDocument() {
        if (document != null) {
            document.removeUndoableEditListener(view.getUndoListener());
        }
        
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();

        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }

    public void setPlainText(String text) {
        resetDocument();
        StringReader stringReader = new StringReader(text);
        try {
            new HTMLEditorKit().read(stringReader, document, 0);
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText() {
        StringWriter stringWriter = new StringWriter();
        try {
            new HTMLEditorKit().write(stringWriter, document, 0, document.getLength());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
        return stringWriter.toString();
    }

    public void createNewDocument() {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор"); //тут можно запросить имя нового документа и установить в title
        view.resetUndo();
        currentFile = null;
    }

    public void openDocument() {
        view.selectHtmlTab();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new HTMLFileFilter());
        int returnVal = chooser.showOpenDialog(view);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());

            try {
                FileReader fileReader = new FileReader(currentFile);
                new HTMLEditorKit().read(fileReader, document, 0);
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }

            view.resetUndo();
        }

    }

    public void saveDocument() {
        if (currentFile != null) {
            view.selectHtmlTab();
            // Saving process. Should be refactored to a separate method
            view.setTitle(currentFile.getName());
            try (FileWriter fileWriter = new FileWriter(currentFile))
            {
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        } else saveDocumentAs();
    }

    public void saveDocumentAs() {
        view.selectHtmlTab();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new HTMLFileFilter());
        int returnVal = chooser.showSaveDialog(view);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();
            // Saving process. Should be refactored to a separate method
            view.setTitle(currentFile.getName());
            try (FileWriter fileWriter = new FileWriter(currentFile))
            {
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }

    public void exit() {
        System.exit(0);
    }


}
