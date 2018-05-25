package com.wang.ui;

import com.wang.model.Board;
import com.wang.model.Cell;
import com.wang.model.Fortress;
import com.wang.model.Tank;

import java.util.List;
import java.util.Scanner;

/**
 * Class for accepting/validating user inputs and printing board status
 *
 * @author Di Wang & Xin Wang
 */
public class TextUI {

    public static int[] getUserInput(int rowNum, int colNum) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        System.out.print("Enter your move: ");
        input = scanner.nextLine();
        while (!validateUserInput(input, rowNum, colNum)) {
            System.out.println("Invalid target. Please enter a coordinate such as D10.");
            System.out.print("Enter your move: ");
            input = scanner.nextLine();
        }
        input = input.toLowerCase();
        return new int[]{((int) input.charAt(0)) - 97, Integer.parseInt(input.substring(1)) - 1};
    }

    private static boolean validateUserInput(String input, int rowLimit, int colLimit) {
        if (input == null || "".equals(input) || input.length() < 2 || input.length() > 3) {
            return false;
        }
        input = input.toLowerCase();
        int rowNumber = (int) input.charAt(0);
        int colNumber = -1;
        if (rowNumber < 97 || rowNumber > rowLimit + 97 - 1) {
            return false;
        }

        try {
            String colString = input.substring(1);
            colNumber = Integer.parseInt(colString);
        } catch (Exception e) {
            return false;
        }

        // remember for user input, row from a ~ rowLimit, col from 1 to colLimit
        return (colNumber >= 1 && colNumber <= colLimit);
    }

    public static void printWelcomeMessage(int numOfTanks) {
        System.out.println("Starting game with " + numOfTanks + " tanks.\n" +
                "----------------------------\n" +
                "Welcome to Fortress Defense!\n" +
                "by Di Wang & Xin Wang\n" +
                "----------------------------");
    }

    public static void drawBoard(Fortress fortress, Board board) {
        System.out.println("\nGame Board:\n       1  2  3  4  5  6  7  8  9 10");
        Cell[][] grid = board.getGrid();
        for (int i = 0; i < 10; i++) {
            int character = 65 + i;
            System.out.print("    " + (char) character);
            for (int j = 0; j < 10; j++) {
                Cell cell = grid[i][j];
                if (!cell.isHit()) {
                    System.out.print("  ~");
                } else if (cell.isHit() && cell.isTankCell()) {
                    System.out.print("  X");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        fortress = new Fortress(1200);
        System.out.println("Fortress Structure Left: " + fortress.getHealth() + ".");
    }

    public static void drawCheatBoard(Fortress fortress, Board board) {
        System.out.println("\nGame Board:\n       1  2  3  4  5  6  7  8  9 10");
        Cell[][] grid = board.getGrid();
        for (int i = 0; i < 10; i++) {
            int character = 65 + i;
            System.out.print("    " + (char) character);
            for (int j = 0; j < 10; j++) {
                Cell cell = grid[i][j];
                if (!cell.isHit() && !cell.isTankCell()) {
                    System.out.print("  .");
                } else if (!cell.isHit() && cell.isTankCell()) {
                    System.out.print("  " + cell.getTank().getTankLetter());
                } else if (cell.isHit() && !cell.isTankCell()) {
                    System.out.print("   ");
                } else {
                    System.out.print("  " +
                            Character.toLowerCase(
                                    cell.getTank().getTankLetter()
                            )
                    );
                }
            }
            System.out.println();
        }
        System.out.println("Fortress Structure Left: " + fortress.getHealth() + ".\n" +
                "(Lower case tank letters are where you shot.)\n");
    }

    public static int tankAttackRound(List<Tank> tanks) {
        int totalDamage = 0;
        int numOfTanks = tanks.size();
        for (int i = 0; i < numOfTanks; i++) {
            Tank tank = tanks.get(i);
            if (tank.getDamage() > 0) {
                System.out.println("Alive tank #" + i
                        + " of " + numOfTanks
                        + " shot you for " + tank.getDamage() + "!");
            }
            totalDamage += tank.getDamage();
        }
        return totalDamage;
    }
}
