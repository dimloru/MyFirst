package com.javarush.task.task24.task2413;

import java.util.List;

public class Arkanoid {
    private int width, height;

    private Ball ball;
    private Stand stand;
    private List<Brick> bricks;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Arkanoid (int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setBall (Ball ball) {
        this.ball = ball;
    }

    public Ball getBall() {
        return ball;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public static Arkanoid game;

    public static void main(String[] args) {

    }

    void run() {

    }

    void move() {
        stand.move();
        ball.move();
    }

    void draw(Canvas canvas) {
        stand.draw(canvas);
        ball.draw(canvas);
        for (Brick brick : bricks) {
            brick.draw(canvas);
        }
    }

}
