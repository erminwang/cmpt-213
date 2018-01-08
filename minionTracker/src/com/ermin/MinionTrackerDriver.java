package com.ermin;

import java.util.ArrayList;
import java.util.List;

public class MinionTrackerDriver {

    private static final String TITLE = "Minion Tracker";

    private static final String[] OPTIONS = {"List minions", "Add a new minion", "Remove minion",
            "Attribute an evil deed to a minion", "Debug dump of minion details", "Exit"};

    private static List<Minion> minionList = new ArrayList<>();

    public static void main(String[] args) {
        Menu menu = new Menu(TITLE, OPTIONS);
        menu.displayMenu();
    }
}
