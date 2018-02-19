package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.javarush.task.task30.task3008.MessageType.*;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
//        System.out.println("Введите номер порта");
        int port = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен.");
            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler (Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            connection.send(new Message(NAME_REQUEST));
            Message response = connection.receive();
            String name = response.getData();

            while (response.getType() != USER_NAME ||
                    name.isEmpty() ||
                    connectionMap.containsKey(name)) {
                connection.send(new Message(NAME_REQUEST));
                response = connection.receive();
                name = response.getData();
            }

            connectionMap.put(name, connection);
            connection.send(new Message(NAME_ACCEPTED));

            return name;
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (String name : connectionMap.keySet()) {
            try {
                connectionMap.get(name).send(message);
            } catch (IOException e) {
                System.out.printf("Could not sent message to %s", name);
//                e.printStackTrace();
            }
        }
    }
}
