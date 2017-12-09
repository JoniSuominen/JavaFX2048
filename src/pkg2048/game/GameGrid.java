/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.GridPane;

/**
 * Class containing the 2048 grid, its elements and movement.
 *
 * @author Joni
 */
public class GameGrid {

    private Direction d;
    private int highestTile = 0; //keeps track of the highest tile in the game
    private Random r; //used to random new tiles on grid
    private Tile[][] tiles;
    public boolean moved;

    public GameGrid() {
        // creates a 4 by 4 grid.
        this.tiles = new Tile[4][4];
        this.r = new Random();
        this.moved = false;

    }

    public int move(Direction d) {
        this.d = d;
        System.out.println(this.toString());
        if (d.getRotations() < 4) {
            for (int i = 0; i < d.getRotations(); i++) {
                rotateBoard();
            }
        }

        int score = moveLeft();

        for (int i = 4 - d.getRotations(); i > 0; i--) {
            rotateBoard();
        }

        System.out.println(this.toString());
        System.out.println(this.toString());

        return score;
    }

    /**
     * Moves the grid's elements left on player action
     */
    public int moveLeft() {
        moved = false;
        int score = 0;
        for (int x = 0; x < 4; x++) {
            int max = 0;
            for (int y = 1; y < 4; y++) {
                int j = y;
                while (j > max) {
                    if (tiles[x][j] != null) {

                        int shiftScore = shiftTiles(x, j, x, j - 1);

                        // We check if the tile in front of the current tile was merged
                        // in the process of shifting the tiles - if so we cant merge nor
                        // move any tiles further than that so we set the cap to the merged tile.
                        if (tiles[x][j - 1] != null && tiles[x][j - 1].isMerged()) {
                            max = j - 1;
                        }

                        score += shiftScore;

                    }
                    j--;
                }
            }
        }

        //Check if 
        return score;
    }

    /**
     * Rotates the board by 90 degrees, thus we can use the same method for
     * different directions. Not the most effecient solution but considering the
     * small amount of data I came into conclusion its better this way than
     * using my initial solution of having bunch of methods for different
     * directions.
     */
    public void rotateBoard() {

        Tile[][] newBoard = new Tile[4][4];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                newBoard[y][x] = tiles[4 - x - 1][y];
            }
        }
        tiles = newBoard;
        System.out.println(toString() + "\n");
    }

    /**
     * Returns true if the position at x, y has not been assigned a value or the
     * position has the same value as the one we want to move . Otherwise
     * returns false.
     *
     * @param x x -position of the grids position which we want to check
     * @param y y -position of the grids position wihch we want to check
     * @param value value which we want to assign to the x, y position
     * @return
     * @
     * return returns false if the position has already been assigned with a
     * value which is not the same as the value we are moving
     *
     */
    public int shiftTiles(int fromX, int fromY, int toX, int toY) {
        int score = 0;
        int value = tiles[fromX][fromY].getValue();
        if (tiles[toX][toY] == null) {
            tiles[toX][toY] = tiles[fromX][fromY];
            tiles[fromX][fromY] = null;
            tiles[toX][toY].setX(toX);
            tiles[toX][toY].setY(toY);
            moved = true;
        } else if (tiles[toX][toY].getValue() == tiles[fromX][fromY].getValue()) {
            tiles[toX][toY] = tiles[fromX][fromY];
            tiles[fromX][fromY] = null;
            tiles[toX][toY].setValue(value * 2);
            score += value * 2;
            tiles[toX][toY].setX(toX);
            tiles[toX][toY].setY(toY);
            tiles[toX][toY].setMerged(true);
            moved = true;
        }

        return score;
    }

    public boolean canMove(int x, int y) {
        return x < 3 && x > 0 && y > 0 && y < 3;
    }

    public void randomNewTile() {
        int x = r.nextInt(4);
        int y = r.nextInt(4);
        while (tiles[x][y] != null) {
            x = r.nextInt(4);
            y = r.nextInt(4);
            System.out.println(x);
            System.out.println(y);
        }
        if (highestTile > 4) {
            double odds = r.nextDouble();
            if (odds < 0.3) {
                tiles[x][y] = new Tile(4);
                tiles[x][y].setValue(4);
                tiles[x][y].update();
                tiles[x][y].setX(x);
                tiles[x][y].setY(y);
                return;
            }
        }
        tiles[x][y] = new Tile(2);
        tiles[x][y].setX(x);
        tiles[x][y].setY(y);
        tiles[x][y].setValue(2);
        tiles[x][y].update();
    }

    /**
     *
     * @return false if game is not over
     */
    public boolean isGameOver() {
        return !(hasEmptySpots() || canMerge());
    }

    public boolean canMerge() {

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {

                if (canMove(y, x)) {

                    if (tiles[y][x + 1] == tiles[y][x]) {
                        return true;
                    } else if (tiles[y][x - 1] == tiles[y][x]) {
                        return true;
                    } else if (tiles[y + 1][x] == tiles[y][x]) {
                        return true;
                    } else if (tiles[y - 1][x] == tiles[y][x]) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public boolean hasEmptySpots() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {

                if (tiles[y][x] == null) {
                    return true;
                }
            }

        }
        return false;
    }

    public int getHighestTile() {
        return this.highestTile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                if (tiles[x][y] == null) {
                    sb.append("0").append(" ");
                } else {
                    sb.append(tiles[x][y].getValue()).append(" ");
                }

            }
            sb.append(("\n"));
        }
        return sb.toString();
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

}
