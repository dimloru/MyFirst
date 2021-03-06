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

    public static void main(String[] args) throws InterruptedException {
        List<Horse> myHorses = new ArrayList<>();
        myHorses.add(new Horse("Belka", 3, 0));
        myHorses.add(new Horse("Strelka", 3, 0));
        myHorses.add(new Horse("Pushok", 3, 0));
        // doesn't work as game.horse.add..... as it is an action with a private field
        game = new Hippodrome(myHorses);


        game.run();
        game.printWinner();

    }

    public void move() {
//        game.getHorses().forEach(horse -> horse.move());
        // Method of this object, that has the field "horses"
        horses.forEach(horse -> horse.move());
    }

    public void print() {
        horses.forEach(horse -> horse.print());
        for (int i = 0; i < 10; i++) System.out.println();
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            move();
            print();
            Thread.sleep(200);
        }
    }

    public Horse getWinner() {
        Horse winner = horses.get(0);
        for (Horse horse : horses) {
            if (horse.getDistance() > winner.getDistance()) {
                winner = horse;
            }
        }
        return winner;
    }

    public void printWinner() {
        System.out.println("Winner is " + getWinner().getName() + "!");
    }
}
