package com.tetromino.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;
import com.tetromino.game.Tetromino;
import com.tetromino.game.TetrominoGame;

/**
 * Created by andre on 3/18/17.
 */
public class GameScreen implements Screen {
    private static final int xScoreLabel = 400;
    private static final int yScoreLabel = 350;
    private static final int offsetTetromino = 350;
    private final TetrominoGame game;
    private final int[][] grid;
    private final Texture background;
    private Tetromino tetromino;
    private long startTime;
    private long time;
    private long score;
    private int lines = 0;
    private final BitmapFont font;
    private boolean paused = false;
    private final BitmapFont pauseFont;
    private boolean gameOver = false;

    public GameScreen(final TetrominoGame game) {
        this.game = game;
        startTime = TimeUtils.nanoTime();
        time = 1000000000;
        score = 0;
        font = new BitmapFont();
        pauseFont = new BitmapFont();
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
        Gdx.gl.glClearColor(0.2f, 0.4f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TimeUtils.timeSinceNanos(startTime) > time) {
            if (!paused) {
                tetromino.increaseRow();
            }
            startTime = TimeUtils.nanoTime();
        }

        clearLines();
        gameOver();

        if (!tetrominoLanded() && !paused) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                paused = true;
            }
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

        } else if (paused) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                paused = false;
            }

        } else {
            tetromino = new Tetromino();
        }
        game.batch.begin();
        font.draw(game.batch, "Score", xScoreLabel, yScoreLabel);
        font.draw(game.batch, String.valueOf(score), xScoreLabel, 335);

        renderBackground();
        renderTetromino();
        renderGrid();

        float yPauseLabel = 300;
        float xPauseLabel = 300;
        if (paused) {
            pauseFont.getData().setScale(2, 2);
            if (!gameOver) {
                pauseFont.draw(game.batch, "PAUSE", xPauseLabel, yPauseLabel);
            }
        }

        if (gameOver) {
            pauseFont.getData().setScale(2, 2);
            pauseFont.draw(game.batch, "GAMEOVER", xPauseLabel, yPauseLabel);
            paused = true;
        }
        game.batch.end();
    }

    private void gameOver() {
        if (grid[0][4] != 0 && grid[1][4] != 0 || grid[0][5] != 0 && grid[1][5] != 0 || grid[0][6] != 0 && grid[1][6] != 0) {
            gameOver = true;
        }
    }

    private void renderBackground() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    game.batch.draw(
                            background, j * -15 + offsetTetromino, i * -15 + offsetTetromino
                    );
                }
            }
        }
    }

    private void clearLines() {
        for (int row = 0; row < grid.length; row++) {
            boolean isFilled = true;
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) {
                    isFilled = false;
                }
            }
            if (isFilled) {
                lines++;
                for (int i = 0; i < grid[row].length; i++) {
                    grid[row][i] = 0;
                }
                System.arraycopy(grid, 0, grid, 1, row);
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
        font.dispose();
        pauseFont.dispose();
    }

    private void renderGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    game.batch.draw(
                            tetromino.i_block, j * -15 + offsetTetromino, i * -15 + offsetTetromino
                    );
                } else if (grid[i][j] == 2) {
                    game.batch.draw(
                            tetromino.o_block, j * -15 + offsetTetromino, i * -15 + offsetTetromino
                    );
                } else if (grid[i][j] == 3) {
                    game.batch.draw(
                            tetromino.t_block, j * -15 + offsetTetromino, i * -15 + offsetTetromino
                    );
                } else if (grid[i][j] == 4) {
                    game.batch.draw(
                            tetromino.j_block, j * -15 + offsetTetromino, i * -15 + offsetTetromino
                    );
                } else if (grid[i][j] == 5) {
                    game.batch.draw(
                            tetromino.l_block, j * -15 + offsetTetromino, i * -15 + offsetTetromino
                    );
                } else if (grid[i][j] == 6) {
                    game.batch.draw(
                            tetromino.s_block, j * -15 + offsetTetromino, i * -15 + offsetTetromino
                    );
                } else if (grid[i][j] == 7) {
                    game.batch.draw(
                            tetromino.z_block, j * -15 + offsetTetromino, i * -15 + offsetTetromino
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
                            (j * -15) - (tetromino.getCol() * 15) + offsetTetromino,
                            (i * -15) - (tetromino.getRow() * 15) + offsetTetromino
                    );
                } else if (tetromino.getShape()[i][j] == 2) {
                    game.batch.draw(
                            tetromino.o_block,
                            (j * -15) - (tetromino.getCol() * 15) + offsetTetromino,
                            (i * -15) - (tetromino.getRow() * 15) + offsetTetromino
                    );
                } else if (tetromino.getShape()[i][j] == 3) {
                    game.batch.draw(
                            tetromino.t_block,
                            (j * -15) - (tetromino.getCol() * 15) + offsetTetromino,
                            (i * -15) - (tetromino.getRow() * 15) + offsetTetromino
                    );
                } else if (tetromino.getShape()[i][j] == 4) {
                    game.batch.draw(
                            tetromino.j_block,
                            (j * -15) - (tetromino.getCol() * 15) + offsetTetromino,
                            (i * -15) - (tetromino.getRow() * 15) + offsetTetromino
                    );
                } else if (tetromino.getShape()[i][j] == 5) {
                    game.batch.draw(
                            tetromino.l_block,
                            (j * -15) - (tetromino.getCol() * 15) + offsetTetromino,
                            (i * -15) - (tetromino.getRow() * 15) + offsetTetromino
                    );
                } else if (tetromino.getShape()[i][j] == 6) {
                    game.batch.draw(
                            tetromino.s_block,
                            (j * -15) - (tetromino.getCol() * 15) + offsetTetromino,
                            (i * -15) - (tetromino.getRow() * 15) + offsetTetromino
                    );
                } else if (tetromino.getShape()[i][j] == 7) {
                    game.batch.draw(
                            tetromino.z_block,
                            (j * -15) - (tetromino.getCol() * 15) + offsetTetromino,
                            (i * -15) - (tetromino.getRow() * 15) + offsetTetromino
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
                    if (col + tetromino.getCol() + 1 < grid[row].length) {
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

