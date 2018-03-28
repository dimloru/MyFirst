package com.javarush.task.task35.task3513;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    public int score;
    public int maxTile;
    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    public void resetGameTiles() {
        score = 0;
        maxTile = 2;
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }


    private List<Tile> getEmptyTiles() {
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) result.add(gameTiles[i][j]);
            }
        }
        return result;
    }

    private void addTile() {
        List<Tile> list = getEmptyTiles();
        if (!list.isEmpty()) {
            int num = (int) (Math.random() * list.size());
            if (num == list.size()) num--;
            list.get(num).setValue(Math.random() < 0.9 ? 2 : 4);
        }
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].value == 0) return true;
                if (j < FIELD_WIDTH - 1) {
                    if (gameTiles[i][j].value == gameTiles[i][j + 1].value) return true;
                }
                if (i < FIELD_WIDTH - 1) {
                    if (gameTiles[i][j].value == gameTiles[i+1][j].value) return true;
                }
            }
        }
        return false;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean changed = false;
        if (tiles != null) {
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i].getValue() == 0) {
                    for (int j = i; j < tiles.length; j++) {
                        if (tiles[j].getValue() != 0) {
                            tiles[i].setValue(tiles[j].getValue());
                            tiles[j].setValue(0);
                            changed = true;
                            break;
                        }
                    }
                }
                if (tiles[i].getValue() == 0) break;
            }
        }
        return changed;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean changed = false;
        if (tiles != null) {
            for (int i = 0; i < tiles.length - 1; i++) {
                if (tiles[i].value == tiles[i + 1].value && tiles[i].value != 0) {
                    tiles[i].value *= 2;
                    tiles[i + 1].value = 0;
                    score += tiles[i].value;
                    if (tiles[i].value > maxTile) maxTile = tiles[i].value;
                    changed = true;
                }
            }
            compressTiles(tiles);
        }
        return changed;
    }

    public void left() {
        if (isSaveNeeded) saveState(gameTiles);
        boolean changed = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i])) changed = true;
            if (mergeTiles(gameTiles[i])) changed = true;
        }
        if (changed) addTile();
        isSaveNeeded = true;
    }

    private void turnClockwise() {
        Tile[][] result = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                result[j][FIELD_WIDTH - 1 - i] = gameTiles[i][j];
            }
        }
        gameTiles = result;
    }

    public void down() {
        saveState(gameTiles);
        turnClockwise();
        left();
        turnClockwise();
        turnClockwise();
        turnClockwise();
    }

    public void right() {
        saveState(gameTiles);
        turnClockwise();
        turnClockwise();
        left();
        turnClockwise();
        turnClockwise();

    }

    public void up() {
        saveState(gameTiles);
        turnClockwise();
        turnClockwise();
        turnClockwise();
        left();
        turnClockwise();
    }

    private void saveState(Tile[][] source) {
        if(source != null && source.length != 0 && source[0].length != 0) {
            Tile[][] toSave = new Tile[source.length][source[0].length];
            for (int i = 0; i < source.length; i++) {
                for (int j = 0; j < source[0].length; j++) {
                    toSave[i][j] = new Tile(source[i][j].getValue());
                }
            }
            previousStates.push(toSave);
            previousScores.push(score);
            isSaveNeeded = false;
        }
    }

    public void rollback() {
        if (!previousStates.isEmpty() && !previousScores.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    public void randomMove() {
        int n = ((int)(Math.random() * 100)) % 4;
        switch (n) {
            case 0:
                left();
                break;
            case 1:
                up();
                break;
            case 2:
                right();
                break;
            case 3:
                down();
                break;
        }
    }
}
