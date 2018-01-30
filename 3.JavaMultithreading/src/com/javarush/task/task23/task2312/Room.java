package com.javarush.task.task23.task2312;

public class Room {
    private int width, height;
    private Snake snake;
    private Mouse mouse;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public static Room game;

    public static void main(String[] args) {
        game = new Room(100, 100, new Snake(50,50));
        game.getSnake().setDirection(SnakeDirection.DOWN);

    }

    void run() {

    }

    void print() {

    }
}
