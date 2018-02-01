package com.javarush.task.task24.task2413;

public class Ball extends BaseObject {
    private double speed;
    private double direction; // 0-360 degrees
    private double dx, dy; // being calculated; distance / 1 step
    private boolean isFrozen;

    public Ball(double x, double y, double speed, double direction) {
        super(x, y, 1);
        this.speed = speed;
        this.direction = direction;
        isFrozen = true;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
        double angle = Math.toRadians(direction);
        dx = Math.cos(angle) * speed;
        dy = -Math.sin(angle) * speed;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    void draw(Canvas canvas) {
        canvas.setPoint(x, y, 'O');

    }

    @Override
    void move() {
        if (!isFrozen) {
            x += dx;
            y += dy;
        }
    }

    void start() {
        isFrozen = false;
    }

    void checkRebound(int minx, int maxx, int miny, int maxy) {
        
    }

}
