package com.wang.model;

/**
 * a class representing the player's peace-loving fortress
 *
 * @author Di Wang & Xin Wang
 */
public class Fortress {
    private int health;

    public Fortress(int health) {
        this.health = health;
    }

    public void reduceHealth(int damageDeal) {
        health -= damageDeal;
        if (health < 0) {
            health = 0;
        }
    }

    public int getHealth() {
        return health;
    }
}
