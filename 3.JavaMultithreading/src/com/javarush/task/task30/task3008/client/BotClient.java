package com.javarush.task.task30.task3008.client;

public class BotClient extends Client {
    public static void main(String[] args) {
        new BotClient().run();
    }


    public class BotSocketThread extends SocketThread {

    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        int botNumber = (int)(Math.random() * 100);
        return "date_bot_" + botNumber;
    }
}
