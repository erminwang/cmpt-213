package com.wang.model;

/**
 * a class for cells on the board, they can either be tank cells or normal cells
 *
 * @author Di Wang & Xin Wang
 */
public class Cell {
    private int id;
    private int row;
    private int col;
    private boolean isHit;
    private Tank tank;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.id = row * 10 + col;
        this.isHit = false;
        this.tank = null;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public boolean isHit() {
        return isHit;
    }

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isTankCell() {
        return tank != null;
    }

    public boolean sufferAttack() {
        isHit = true;
        if (isTankCell()) {
            tank.calculateDamage();
            return true;
        }
        return false;
    }
}
