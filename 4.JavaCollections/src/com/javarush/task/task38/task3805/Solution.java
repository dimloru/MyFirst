package com.javarush.task.task38.task3805;

/* 
Улучшения в Java 7 (multiple exceptions)
*/

public class Solution {
    private final Connection connection;

    public Solution() throws SolutionException {
        try {
            connection = new ConnectionMock();
            connection.connect();
        }
        catch (WrongDataException | ConnectionException ex) {
            throw new SolutionException(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    public void write(Object data) throws SolutionException {
        try {
            connection.write(data);
        }
        catch (WrongDataException | ConnectionException ex) {
            throw new SolutionException(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    public Object read() throws SolutionException {
        try {
            return connection.read();
        }
        catch (WrongDataException | ConnectionException ex) {
            throw new SolutionException(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    public void disconnect() throws SolutionException {
        try {
            connection.disconnect();
        }
        catch (WrongDataException | ConnectionException ex) {
            throw new SolutionException(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
//        try {
////            Solution s = new Solution();
////            s.write(new Object());
////            s.read();
////            s.disconnect();
//        } catch (SolutionException e) {
//            System.out.println(e);
//            System.out.println(e.getMessage());
//            System.out.println(e.getClass().getSimpleName());
//        }

    }
}
