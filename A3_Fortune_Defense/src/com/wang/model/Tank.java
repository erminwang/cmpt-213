package com.wang.model;

import java.util.List;

/**
 * a class representing tanks that are placed on the board
 *
 * @author Di Wang & Xin Wang
 */
public class Tank {
    private final int INITIAL_DAMAGE = 20;
    private int damage;
    private char tankLetter;
    private List<Cell> tankCells;

    Tank(int tankNumber) {
        this.damage = INITIAL_DAMAGE;
        this.tankLetter = (char) (65 + tankNumber);
    }

    private int getNumOfAliveCell() {
        int numOfCell = 0;

        for (Cell tankCell : tankCells) {
            numOfCell = tankCell.isHit() ? numOfCell : numOfCell + 1;
        }
        assert numOfCell >= 0 && numOfCell <= 4 : "Tank has invalid number of cells: " + numOfCell;
        return numOfCell;
    }

    public void calculateDamage() {
        int numCell = getNumOfAliveCell();
        switch (numCell) {
            case 4:
                damage = 20;
                break;
            case 3:
                damage = 5;
                break;
            case 2:
                damage = 2;
                break;
            case 1:
                damage = 1;
                break;
            case 0:
                damage = 0;
                break;
            default:
                assert false : "Invalid tank cell number: " + numCell;
        }
    }

    public void setTankCells(List<Cell> tankCells) {
        this.tankCells = tankCells;
    }

    public char getTankLetter() {
        return tankLetter;
    }

    public int getDamage() {
        return damage;
    }
}
