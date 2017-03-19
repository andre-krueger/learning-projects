package com.tetromino.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.tetromino.game.Tetromino;
import com.tetromino.game.TetrominoGame;

/**
 * Created by andre on 3/18/17.
 */
public class GameScreen implements Screen {
    private final TetrominoGame game;
    private final int[][] grid;
    private final Texture background;
    private Tetromino tetromino;
    private long startTime;
    private long time;
    private long score;
    private int lines = 0;

    public GameScreen(final TetrominoGame game) {
        this.game = game;
        startTime = TimeUtils.nanoTime();
        time = 1000000000;
        score = 0;
        background = new Texture(Gdx.files.internal("block.png"));
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
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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

        if (TimeUtils.timeSinceNanos(startTime) > time) {
            tetromino.increaseRow();
            startTime = TimeUtils.nanoTime();
        }

        clearLines();
        if (!tetrominoLanded()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                tetromino.increaseRow();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                time = 0;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                if (canRotate()) {
                    tetromino.rotateShape();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                if (canMoveLeft()) {
                    tetromino.increaseCol();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                if (canMoveRight()) {
                    tetromino.decreaseCol();
                }
            }

        } else if (gameOver()) {
            System.exit(0);
        } else {
            tetromino = new Tetromino();
        }
        game.batch.begin();
        renderBackground();
        renderTetromino();
        renderGrid();
        game.batch.end();
        System.out.println(score);
    }

    private boolean gameOver() {
        if (grid[0][4] != 0 && grid[1][4] != 0 || grid[0][5] != 0 && grid[1][5] != 0 || grid[0][6] != 0 && grid[1][6] != 0) {
            return true;
        }
        return false;
    }

    private void renderBackground() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    game.batch.draw(
                            background, j * -15 + 300, i * -15 + 300
                    );
                }
            }
        }
    }

    private void clearLines() {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) {
                    count = 0;
                    break;
                } else if (count == 10) {


                    for (int i = 0; i < grid[row].length; i++) {
                        grid[row][i] = 0;

                    }
                    System.out.println("clear");

                    // shift array down
                    for (int i = row; i > 0; i--) {
                        grid[i] = grid[i - 1];
                    }
                    ++lines;

                } else {
                    count++;
                }
            }

        }
        if (lines == 1) {
            score += 40;
            lines = 0;
        } else if (lines == 2) {
            score += 100;
            lines = 0;
        } else if (lines == 3) {
            score += 300;
            lines = 0;
        } else if (lines == 4) {
            score += 1200;
            lines = 0;
        }
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
        tetromino.i_block.dispose();
        tetromino.o_block.dispose();
        tetromino.t_block.dispose();
        tetromino.j_block.dispose();
        tetromino.l_block.dispose();
        tetromino.s_block.dispose();
        tetromino.z_block.dispose();
        background.dispose();
    }

    private void renderGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    game.batch.draw(
                            tetromino.i_block, j * -15 + 300, i * -15 + 300
                    );
                } else if (grid[i][j] == 2) {
                    game.batch.draw(
                            tetromino.o_block, j * -15 + 300, i * -15 + 300
                    );
                } else if (grid[i][j] == 3) {
                    game.batch.draw(
                            tetromino.t_block, j * -15 + 300, i * -15 + 300
                    );
                } else if (grid[i][j] == 4) {
                    game.batch.draw(
                            tetromino.j_block, j * -15 + 300, i * -15 + 300
                    );
                } else if (grid[i][j] == 5) {
                    game.batch.draw(
                            tetromino.l_block, j * -15 + 300, i * -15 + 300
                    );
                } else if (grid[i][j] == 6) {
                    game.batch.draw(
                            tetromino.s_block, j * -15 + 300, i * -15 + 300
                    );
                } else if (grid[i][j] == 7) {
                    game.batch.draw(
                            tetromino.z_block, j * -15 + 300, i * -15 + 300
                    );
                }
            }
        }
    }

    private void renderTetromino() {
        for (int i = 0; i < tetromino.getShape().length; i++) {
            for (int j = 0; j < tetromino.getShape()[i].length; j++) {
                if (tetromino.getShape()[i][j] == 1) {
                    game.batch.draw(
                            tetromino.i_block,
                            (j * -15) - (tetromino.getCol() * 15) + 300,
                            (i * -15) - (tetromino.getRow() * 15) + 300
                    );
                } else if (tetromino.getShape()[i][j] == 2) {
                    game.batch.draw(
                            tetromino.o_block,
                            (j * -15) - (tetromino.getCol() * 15) + 300,
                            (i * -15) - (tetromino.getRow() * 15) + 300
                    );
                } else if (tetromino.getShape()[i][j] == 3) {
                    game.batch.draw(
                            tetromino.t_block,
                            (j * -15) - (tetromino.getCol() * 15) + 300,
                            (i * -15) - (tetromino.getRow() * 15) + 300
                    );
                } else if (tetromino.getShape()[i][j] == 4) {
                    game.batch.draw(
                            tetromino.j_block,
                            (j * -15) - (tetromino.getCol() * 15) + 300,
                            (i * -15) - (tetromino.getRow() * 15) + 300
                    );
                } else if (tetromino.getShape()[i][j] == 5) {
                    game.batch.draw(
                            tetromino.l_block,
                            (j * -15) - (tetromino.getCol() * 15) + 300,
                            (i * -15) - (tetromino.getRow() * 15) + 300
                    );
                } else if (tetromino.getShape()[i][j] == 6) {
                    game.batch.draw(
                            tetromino.s_block,
                            (j * -15) - (tetromino.getCol() * 15) + 300,
                            (i * -15) - (tetromino.getRow() * 15) + 300
                    );
                } else if (tetromino.getShape()[i][j] == 7) {
                    game.batch.draw(
                            tetromino.z_block,
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
                    if (row + tetromino.getRow() + 1 >= grid.length) {
                        addTetrominoToGrid();
                        return true;
                    } else if (grid[row + tetromino.getRow() + 1][col + tetromino.getCol()] != 0) {
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
                    time = 1000000000;
                }
            }
        }
    }

    private boolean canMoveRight() {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    if (col + tetromino.getCol() - 1 < 0) {
                        return false;
                    }
                    if (grid[row + tetromino.getRow()][col + tetromino.getCol() - 1] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean canMoveLeft() {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    if (col + tetromino.getCol() + 1 >= grid[0].length) {
                        return false;
                    }
                    if (grid[row + tetromino.getRow()][col + tetromino.getCol() + 1] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean canRotate() {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    if (col + tetromino.getCol() - 1 < 0) {
                        return false;
                    }
                    if (grid[row + tetromino.getRow()][col + tetromino.getCol() - 1] != 0) {
                        return false;
                    }
                    if (col + tetromino.getCol() + 1 < 10) {
                        if (grid[row + tetromino.getRow()][col + tetromino.getCol() + 1] != 0) {
                            return false;
                        }
                    }
                    if (tetromino.getShape().length == 4 && tetromino.getCol() == 8) {
                        return false;
                    }
                    if (tetromino.getShape().length == 4 && tetromino.getCol() == 7) {
                        tetromino.decreaseCol();
                    }
                    if (tetromino.getCol() + col >= 9) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

