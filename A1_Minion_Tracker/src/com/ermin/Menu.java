package com.ermin;

import java.util.Scanner;

/**
 * A class for displaying and managing menu
 * Used by class MiniontrackerDriver
 *
 * @author Ermin Wang
 */
public class Menu {

    private static final String WELCOME_TO = "Welcome to ";

    private String title;
    private String[] options;

    Menu(String title, String[] options) {
        this.title = title;
        this.options = options;
    }

    public String[] getOptions() {
        return this.options;
    }

    public void displayMenuTitle() {
        int titleLength = title.length();
        printAsterisks(WELCOME_TO.length() + titleLength);
        System.out.println("\n" + WELCOME_TO + title + "\nby Ermin Wang.");
        printAsterisks(WELCOME_TO.length() + titleLength);
        System.out.println();
    }

    public void displayMenuOptions() {
        System.out.println("\n*************\n" +
                "* Main Menu *\n" +
                "*************");
        for (int i = 1; i <= options.length; i++) {
            System.out.println(i + ". " + options[i - 1]);
        }
    }

    private void printAsterisks(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
    }

    /**
     * Get user input as Integer
     *
     * @return the user input
     */
    public int getSelection() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextInt();
    }

}
