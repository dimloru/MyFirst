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

        @Override
        public void run() {
            String name = null;
            ConsoleHelper.writeMessage(socket.getRemoteSocketAddress() + " connected");
            try (Connection connection = new Connection(socket)) {
                name = serverHandshake(connection);
                sendBroadcastMessage(new Message(USER_ADDED, name));
                sendListOfUsers(connection, name);
                serverMainLoop(connection, name);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("An error occured while working with remote address");
            } catch (ClassNotFoundException e) {
                ConsoleHelper.writeMessage("An error occured while working with remote address");
            } finally {
                if (name != null) {
                    connectionMap.remove(name);
                    sendBroadcastMessage(new Message(USER_REMOVED, name));
                    ConsoleHelper.writeMessage("Connection to remote address " + socket + " closed");
                }
            }
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

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (String user : connectionMap.keySet()) {
                if (!user.equals(userName)) {
                    Message msg = new Message(USER_ADDED, user);
                    connection.send(msg);
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message msg = connection.receive();
                if (msg.getType() == TEXT) {
                    sendBroadcastMessage(new Message(TEXT, userName + ": " + msg.getData()));
                } else {
                    ConsoleHelper.writeMessage("Not a TEXT");
                }
            }
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (String name : connectionMap.keySet()) {
            try {
                connectionMap.get(name).send(message);
            } catch (IOException e) {
                System.out.printf("Could not sent message to %s", name);
            }
        }
    }


}
