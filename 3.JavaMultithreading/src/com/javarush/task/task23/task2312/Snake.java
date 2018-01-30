package com.javarush.task.task23.task2312;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public List<SnakeSection> getSections() {
        return sections;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public void move() {
        if (isAlive) {
            switch (direction) {
                case UP:
                    move(0, -1);
                    break;
                case DOWN:
                    move(0, 1);
                    break;
                case LEFT:
                    move(-1, 0);
                    break;
                case RIGHT:
                    move(1, 0);
                    break;
            }
        }
    }

    public void move(int x, int y) {

    }

    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public void checkBorders(SnakeSection head) {
        if (head.getX() < 0 || head.getX() > (Room.game.getWidth() - 1) ||
                head.getY() < 0 || head.getY() > Room.game.getHeight() - 1) isAlive = false;
    }

    public void checkBody(SnakeSection head) {
        if (sections.contains(head)) isAlive = false;
    }



}
