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
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
        maxTile = 2;
    }


    public List<Tile> getEmptyTiles() {
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
        int num = (int) (Math.random() * list.size());
        if (num == list.size()) num--;
        list.get(num).setValue(Math.random() < 0.9 ? 2 : 4);
    }

    private Tile[] compressTiles(Tile[] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getValue() == 0) {
                for (int j = i; j < tiles.length; j++) {
                    if (tiles[j].getValue() != 0) {
                        tiles[i].setValue(tiles[j].getValue());
                        tiles[j].setValue(0);
                        break;
                    }
                }
            }
            if (tiles[i].getValue() == 0) break;
        }
        return tiles;
    }

    private Tile[] mergeTiles(Tile[] tiles) {
        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i].value == tiles [i + 1].value && tiles[i].value != 0) {
                tiles[i].value *= 2;
                tiles[i + 1].value = 0;
                score += tiles[i].value;
                if (tiles[i].value > maxTile) maxTile = tiles[i].value;
            }
        }
        tiles = compressTiles(tiles);
        return tiles;
    }

}
