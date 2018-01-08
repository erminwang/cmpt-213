package com.ermin;

public class Minion {

    private String name;
    private double height;
    private int numOfEvilDeeds;

    public Minion(String name, double height, int numOfEvilDeeds) {
        this.name = name;
        this.height = height;
        this.numOfEvilDeeds = numOfEvilDeeds;
    }


    @Override
    public String toString() {
        return getClass().getName() + " \"" + name + "\" of height " + height + " has done " + numOfEvilDeeds + " evil deeds.";
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

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setNumOfEvilDeeds(int numOfEvilDeeds) {
        this.numOfEvilDeeds = numOfEvilDeeds;
    }
}
