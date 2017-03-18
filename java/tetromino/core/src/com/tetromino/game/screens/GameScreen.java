package com.tetromino.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.tetromino.game.Tetromino;
import com.tetromino.game.TetrominoGame;

/**
 * Created by andre on 3/18/17.
 */
public class GameScreen implements Screen {
    final TetrominoGame game;
    final Texture tetrominoTexture;
    int[][] grid;
    int shapeCol;
    int shapeRow;
    private Tetromino tetromino;

    public GameScreen(final TetrominoGame game) {
        this.game = game;
        tetrominoTexture = new Texture(Gdx.files.internal("block.png"));
        grid = new int[][]{
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 1, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };

        placeTetronimo();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (!tetronimoCollided()) {
                ++shapeRow;
            } else {
                placeTetronimo();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (!tetronimoCollided()) {
                tetromino.rotateShape();
            } else {
                placeTetronimo();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (!tetronimoCollided()) {
                ++shapeCol;
            } else {
                placeTetronimo();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (!tetronimoCollided()) {
                --shapeCol;
            } else {
                placeTetronimo();
            }
        }
        game.batch.begin();
        renderTetronimo();
        renderGrid();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        tetrominoTexture.dispose();
    }

    private void renderGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    game.batch.draw(tetrominoTexture, j * -15 + 300, i * -15 + 300);
                }
            }
        }
    }

    private void placeTetronimo() {
        tetromino = new Tetromino();
        shapeRow = 0;
        shapeCol = 5;
    }

    private void renderTetronimo() {
        for (int i = 0; i < tetromino.getShape().length; i++) {
            for (int j = 0; j < tetromino.getShape()[i].length; j++) {
                if (tetromino.getShape()[i][j] != 0) {
                    game.batch.draw(tetrominoTexture, (j * -15) - (shapeCol * 15) + 300, (i * -15) - (shapeRow * 15) + 300);
                }
            }
        }
    }

    private boolean tetronimoCollided() {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    if (grid[row + shapeRow + 1][col + shapeCol] != 0) {
                        System.out.println("ee");
                        addTetrominoToGrid();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void addTetrominoToGrid() {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    grid[row + shapeRow][col + shapeCol] = tetromino.getShape()[row][col];
                }
            }
        }
    }

    private void rotateTetromino() {

    }
}
