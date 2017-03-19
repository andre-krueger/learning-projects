package com.tetromino.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by andre on 3/18/17.
 */
public class Tetromino {
    private int rotation;
    final LinkedHashMap<String, List<int[][]>> tetrominos;
    int row;
    int col;
    String key;
    private boolean canMoveLeft = true;
    private boolean canMoveRight = true;

    public Tetromino() {
        row = 0;
        col = 4;

        ArrayList<int[][]> i = new ArrayList<>();
        i.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
        });
        i.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
        });

        ArrayList<int[][]> o = new ArrayList<>();
        o.add(new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
        });

        ArrayList<int[][]> t = new ArrayList<>();
        t.add(new int[][]{
                {1, 1, 1},
                {0, 1, 0},
        });
        t.add(new int[][]{
                {0, 1},
                {1, 1},
                {0, 1}
        });
        t.add(new int[][]{
                {0, 1, 0},
                {1, 1, 1},
        });
        t.add(new int[][]{
                {1, 0},
                {1, 1},
                {1, 0},
        });

        ArrayList<int[][]> j = new ArrayList<>();
        j.add(new int[][]{
                {1, 1, 1},
                {0, 0, 1},
        });
        j.add(new int[][]{
                {0, 1},
                {0, 1},
                {1, 1}
        });
        j.add(new int[][]{
                {1, 0, 0},
                {1, 1, 1},
        });
        j.add(new int[][]{
                {1, 1},
                {1, 0},
                {1, 0},
        });
        ArrayList<int[][]> l = new ArrayList<>();
        l.add(new int[][]{
                {1, 1, 1},
                {1, 0, 0},
        });
        l.add(new int[][]{
                {1, 1},
                {0, 1},
                {0, 1}
        });
        l.add(new int[][]{
                {0, 0, 1},
                {1, 1, 1},
        });
        l.add(new int[][]{
                {1, 0},
                {1, 0},
                {1, 1},
        });

        ArrayList<int[][]> s = new ArrayList<>();
        s.add(new int[][]{
                {0, 1, 1},
                {1, 1, 0},
        });
        s.add(new int[][]{
                {1, 0},
                {1, 1},
                {0, 1}
        });

        ArrayList<int[][]> z = new ArrayList<>();
        z.add(new int[][]{
                {1, 1, 0},
                {0, 1, 1},
        });
        z.add(new int[][]{
                {0, 1},
                {1, 1},
                {1, 0}
        });

        tetrominos = new LinkedHashMap<>();
        tetrominos.put("i", i);
        tetrominos.put("o", o);
        tetrominos.put("t", t);
        tetrominos.put("j", j);
        tetrominos.put("l", l);
        tetrominos.put("s", s);
        tetrominos.put("z", z);

        key = "";

        Random r = new Random();

        String alphabet = "i";

        for (int h = 0; h < 1; h++) {
            key = alphabet.charAt(r.nextInt(alphabet.length())) + "";
        }
    }

    public int[][] getShape() {
        if (rotation >= tetrominos.get(key).size()) {
            rotation = 0;
        }
        return tetrominos.get(key).get(rotation);
    }

    public void rotateShape() {
        rotation++;
    }

    public void increaseRow() {
        row++;
    }

    public void increaseCol() {
        col++;
    }

    public void decreaseCol() {
        col--;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCanMoveLeft(final boolean _canMoveLeft) {
        canMoveLeft = _canMoveLeft;
    }
    public void setCanMoveRight(final boolean _canMoveRight) {
        canMoveRight = _canMoveRight;
    }

    public boolean getCanMoveRight() {
        return canMoveRight;
    }

    public boolean getCanMoveLeft() {
        return canMoveLeft;
    }
}

