package com.tetromino.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by andre on 3/18/17.
 */
public class Tetromino {
    private int rotation;
    final LinkedHashMap<String, List<int[][]>> tetrominos;

    public Tetromino() {
        ArrayList<int[][]> i = new ArrayList<>();
        i.add(new int[][]{
                {1, 1, 1, 1}
        });
        i.add(new int[][]{
                {1},
                {1},
                {1},
                {1},
        });
        tetrominos = new LinkedHashMap<>();
        tetrominos.put("i", i);
    }

    public int[][] getShape() {
        return tetrominos.get("i").get(rotation);
    }
    public void rotateShape() {
        
        rotation++;
    }
}
