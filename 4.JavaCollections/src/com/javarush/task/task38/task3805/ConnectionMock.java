package com.javarush.task.task38.task3805;

public class ConnectionMock implements Connection {
    @Override
    public void connect() throws WrongDataException, ConnectionException {

    }

    @Override
    public void write(Object data) throws WrongDataException, ConnectionException {
//        throw new WrongDataException("wde");

    }

    @Override
    public Object read() throws WrongDataException, ConnectionException {
//        throw new WrongDataException("wde");
        return null;
    }

    @Override
    public void disconnect() throws WrongDataException, ConnectionException {
//        throw new ConnectionException("ce");

    }
}
