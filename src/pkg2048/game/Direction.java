/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048.game;

/**
 *
 * @author Joni
 */
public enum Direction {

    UP(-130, 3),
    DOWN(130, 1),
    RIGHT(130, 2),
    LEFT(-130, 4);

    private int amountOfMovement;
    private int rotations; // number of time the board has to rotate to move

    private Direction(int amountOfMovement, int rotations) {
        this.amountOfMovement = amountOfMovement;
        this.rotations = rotations;
    }

    public int getAmountOfMovement() {
        return amountOfMovement;
    }

    public int getRotations() {
        return rotations;
    }

}
