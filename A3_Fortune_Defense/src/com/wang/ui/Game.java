package com.wang.ui;

import com.wang.model.Board;
import com.wang.model.Fortress;

/**
 * A class for game driver, game logic
 *
 * @author Di Wang & Xin Wang
 */
public class Game {
    private static final int ROW_NUM = 10;
    private static final int COL_NUM = 10;
    private static final int FORTRESS_HEALTH = 1500;
    private static Board board;
    private static Fortress fortress;

    public static void main(String[] args) {
        assert args.length < 3 : "You should only provide 0, 1 or 2 arguments";
        boolean isCheating = false;
        int numOfTanks = 5;
        if (args.length > 0) {
            numOfTanks = Integer.parseInt(args[0]);
        }
        if (args.length == 2 && "--cheat".equalsIgnoreCase(args[1])) {
            isCheating = true;
        }
        initializeGame(numOfTanks);
        if (isCheating) {
            TextUI.drawCheatBoard(fortress, board);
        }
        System.out.println(board.getClass().getName());
        TextUI.printWelcomeMessage(board.getNumOfTanks());
        while (true) {
            TextUI.drawBoard(fortress, board);
            checkGameStatus();
            int[] cellLocation = TextUI.getUserInput(ROW_NUM, COL_NUM);
            if (attackCell(cellLocation, board)) {
                System.out.println("HIT!");
            } else {
                System.out.println("MISS!");
            }
            fortress.reduceHealth(TextUI.tankAttackRound(board.getTanks()));
        }
    }

    private static void initializeGame(int numOfTanks) {
        assert numOfTanks > 0 && numOfTanks < 26 : "Invalid tank numberOfTanks: " + numOfTanks;
        board = new Board(ROW_NUM, COL_NUM);
        fortress = new Fortress(FORTRESS_HEALTH);
        board.createBoard(numOfTanks);
    }

    private static void checkGameStatus() {
        if (fortress.getHealth() <= 0) {
            System.out.println("I'm sorry, your fortress has been smashed!");
            TextUI.drawCheatBoard(fortress, board);
            System.exit(0);
        } else if (board.calculateTotalDamage() <= 0) {
            System.out.println("Congratulations! You won!");
            TextUI.drawCheatBoard(fortress, board);
            System.exit(0);
        }
    }

    private static boolean attackCell(int[] cellLocation, Board board) {
        return board.eliminateCell(cellLocation);
    }
}
