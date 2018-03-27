package com.javarush.task.task35.task3513;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    public int score;
    public int maxTile;

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
        boolean changed = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i])) changed = true;
            if (mergeTiles(gameTiles[i])) changed = true;
        }
        if (changed) addTile();
    }

}
