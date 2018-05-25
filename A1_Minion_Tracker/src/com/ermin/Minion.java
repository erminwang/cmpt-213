package com.ermin;

/**
 * A class for minion implementation
 *
 * @author Ermin Wang
 */
public class Minion {

    private String name;
    private double height;
    private int numOfEvilDeeds;

    Minion(String name, double height) {
        this.name = name;
        this.height = height;
        this.numOfEvilDeeds = 0;
    }

    /**
     * A method for debugging
     *
     * @return all information for a minion as String
     */
    @Override
    public String toString() {
        return getClass().getName() + "[Name:" + name + ", Evil Deeds:" + numOfEvilDeeds + ", Height:" + height + "]";
    }

    public String getName() {
        return this.name;
    }

    public double getHeight() {
        return this.height;
    }

    public int getNumOfEvilDeeds() {
        return this.numOfEvilDeeds;
    }

    /**
     * Increase the number of evil deed of the minion by one
     */
    public void incrementEvilDeeds() {
        this.numOfEvilDeeds++;
        System.out.println(this.name + " has now down " + this.numOfEvilDeeds + " evil deed(s)!");
    }
}
