package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.List;

public class Hippodrome {
    private List<Horse> horses;

    public Hippodrome (List<Horse> horses) {
        this.horses = horses;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public static Hippodrome game;

    public static void main(String[] args) {
        game = new Hippodrome(new ArrayList<Horse>());
        game.horses.add(new Horse("Belka", 3, 0));
        game.horses.add(new Horse("Strelka", 3, 0));
        game.horses.add(new Horse("Pushok", 3, 0));




    }

    public void move() {
//        game.getHorses().forEach(horse -> horse.move());
        horses.forEach(horse -> horse.move());
    }

    public void print() {

    }

    public void run() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            move();
            print();
            Thread.sleep(200);
        }

    }
    
}
