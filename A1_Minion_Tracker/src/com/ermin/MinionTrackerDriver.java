package com.ermin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class for running Minion Tracker program, support operations including listing/adding/deleting minions and
 * debugging
 *
 * @author Ermin Wang
 */
public class MinionTrackerDriver {

    private static final String TITLE = "the Evil Minion Tracker (tm)";

    private static final String[] OPTIONS = {"List minions", "Add a new minion", "Remove minion",
            "Attribute evil deed to a minion", "DEBUG: Dump objects (toString)", "Exit"};

    private static List<Minion> minionList = new ArrayList<>();

    public static void main(String[] args) {
        boolean isPlaying = true;

        Menu menu = new Menu(TITLE, OPTIONS);
        menu.displayMenuTitle();
        menu.displayMenuOptions();

        while (isPlaying) {
            int input = menu.getSelection();
            switch (input) {
                case 1:
                    listAllMinions();
                    menu.displayMenuOptions();
                    break;
                case 2:
                    addMinion();
                    menu.displayMenuOptions();
                    break;
                case 3:
                    deleteMinion();
                    menu.displayMenuOptions();
                    break;
                case 4:
                    attributeEvilDeeds();
                    menu.displayMenuOptions();
                    break;
                case 5:
                    dump();
                    menu.displayMenuOptions();
                    break;
                case 6:
                    isPlaying = false;
                    break;
                default:
                    System.out.println("Error: Please enter a selection between " + 1 +
                            " and " + menu.getOptions().length);
            }
        }
    }

    // List all minions in "minionList" ArrayList
    private static void listAllMinions() {
        System.out.println("\nList of Minions:\n****************");
        if (minionList.size() > 0) {
            for (int i = 0; i < minionList.size(); i++) {
                Minion minion = minionList.get(i);
                System.out.println(i + 1 + ". " + minion.getName() + ", " + minion.getHeight() + "m, " +
                        minion.getNumOfEvilDeeds() + " evil deed(s)");
            }
        } else {
            System.out.println("No minions found.");
        }

    }


    private static void addMinion() {
        Scanner sc = new Scanner(System.in);
        boolean isMinionNameProvided = false;
        boolean isMinionHeightProvided = false;
        String minionName = null;
        double minionHeight = 0.0;

        System.out.print("Enter minion's name: ");
        while (!isMinionNameProvided) {
            minionName = sc.nextLine();
            if (minionName == null || minionName.equals("")) {
                System.out.println("Error: Minion name should not be empty !");
            } else {
                isMinionNameProvided = true;
            }
        }
        //System.out.println(">>> " + minionName);

        System.out.print("Enter minion's height: ");
        while (!isMinionHeightProvided) {
            if (sc.hasNextDouble()) {
                minionHeight = sc.nextDouble();
                isMinionHeightProvided = true;
            } else {
                System.out.println("Please provided a correct double!");
                sc = new Scanner(System.in);
            }
        }
        minionList.add(new Minion(minionName, minionHeight));
    }

    private static void deleteMinion() {

        listAllMinions();
        int minionIndex = getMinionIndex();
        if (minionIndex < 0) {
            return;
        }
        minionList.remove(minionIndex);
    }

    private static void attributeEvilDeeds() {
        listAllMinions();
        int minionIndex = getMinionIndex();
        if (minionIndex < 0) {
            return;
        }
        minionList.get(minionIndex).incrementEvilDeeds();
    }

    private static void dump() {
        System.out.println("All minion objects:");
        for (int i = 1; i <= minionList.size(); i++) {
            System.out.println(i + ". " + minionList.get(i - 1).toString());
        }
    }

    /**
     * get user input of a minion to delete or attribute evil deeds to
     *
     * @return return the index of the minion
     */
    private static int getMinionIndex() {

        System.out.print("(Enter 0 to cancel)\n> ");
        Scanner sc = new Scanner(System.in);
        while (true) {
            int selection = sc.nextInt();
            if (selection >= 1 && selection <= minionList.size()) {
                return selection - 1;
            } else if (selection == 0) {
                return -1;
            } else {
                System.out.print("Error: Please enter a selection between " + 0 + " and " +
                        minionList.size() + "\n> ");
            }
        }
    }
}
