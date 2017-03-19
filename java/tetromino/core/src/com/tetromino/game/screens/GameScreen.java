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
    final int[][] grid;

    private Tetromino tetromino;

    public GameScreen(final TetrominoGame game) {
        this.game = game;
        tetrominoTexture = new Texture(Gdx.files.internal("block.png"));
        grid = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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

        tetromino = new Tetromino();

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!tetrominoLanded()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                tetromino.increaseRow();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                tetromino.rotateShape();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                tetrominoCollided();
                if (!tetromino.getCanMoveRight()) {
                    tetromino.setCanMoveRight(true);
                }
                if (tetromino.getCanMoveLeft()) {
                    tetromino.increaseCol();
                }

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                tetrominoCollided();
                if (!tetromino.getCanMoveLeft()) {
                    tetromino.setCanMoveLeft(true);
                }
                if (tetromino.getCanMoveRight()) {
                    tetromino.decreaseCol();
                }
            }
        } else {
            tetromino = new Tetromino();
        }
        game.batch.begin();
        renderTetromino();
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

    private void renderTetromino() {
        for (int i = 0; i < tetromino.getShape().length; i++) {
            for (int j = 0; j < tetromino.getShape()[i].length; j++) {
                if (tetromino.getShape()[i][j] != 0) {
                    game.batch.draw(
                            tetrominoTexture,
                            (j * -15) - (tetromino.getCol() * 15) + 300,
                            (i * -15) - (tetromino.getRow() * 15) + 300
                    );
                }
            }
        }
    }

    private boolean tetrominoLanded() {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    if (grid[row + tetromino.getRow() + 1][col + tetromino.getCol()] != 0) {
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
                    grid[row + tetromino.getRow()][col + tetromino.getCol()] = tetromino.getShape()[row][col];
                }
            }
        }
    }

    private void tetrominoCollided() {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    if (col + tetromino.getCol() - 1 <= 0) {
                        tetromino.setCanMoveRight(false);
                    }
                    System.out.println(tetromino.getCol() + col);
                    if (col + tetromino.getShape().length + tetromino.getCol()  >= grid[0].length) {
                        tetromino.setCanMoveLeft(false);
                    }
                    if (grid[row + tetromino.getRow()][col + tetromino.getCol()-1] != 0) {
                        tetromino.setCanMoveRight(false);
                    }
                    if (grid[row + tetromino.getRow()][col + tetromino.getCol()+1] != 0) {
                        tetromino.setCanMoveLeft(false);
                    }


                }
            }
        }
    }
}

