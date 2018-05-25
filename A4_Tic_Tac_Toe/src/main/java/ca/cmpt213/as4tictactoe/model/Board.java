package ca.cmpt213.as4tictactoe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A class representing Tic-Tac-Toe game board
 *
 * @author Di Wang
 */
public class Board {

    private String row1;
    private String row2;
    private String row3;

    @JsonIgnore
    private char[] matrix;

    public Board() {
        this.row1 = "     ";
        this.row2 = "     ";
        this.row3 = "     ";
        matrix = new char[9];
        //initialize matrix
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = ' ';
        }
    }

    public String getRow1() {
        return row1;
    }

    public void setRow1(String row1) {
        this.row1 = row1;
    }

    public String getRow2() {
        return row2;
    }

    public void setRow2(String row2) {
        this.row2 = row2;
    }

    public String getRow3() {
        return row3;
    }

    public void setRow3(String row3) {
        this.row3 = row3;
    }

    public char[] getMatrix() {
        return matrix;
    }

    public void setMatrix(char[] matrix) {
        this.matrix = matrix;
    }

    public boolean isOccupied(int row, int col) {
        return matrix[findMatrixIndexByCoordinate(row, col)] != ' ';
    }

    public boolean placePieceAndCheckStatus(char piece, int row, int col) {
        matrix[findMatrixIndexByCoordinate(row, col)] = piece;
        switch (row) {
            case 0:
                row1 = matrix[0] + " " + matrix[1] + " " + matrix[2];
                break;
            case 1:
                row2 = matrix[3] + " " + matrix[4] + " " + matrix[5];
                break;
            case 2:
                row3 = matrix[6] + " " + matrix[7] + " " + matrix[8];
        }
        return checkBoardStatus();
    }

    private int findMatrixIndexByCoordinate(int row, int col) {
        return row * 3 + col;
    }

    private boolean checkBoardStatus() {
        return isSameCharater(0, 1, 2)
                || isSameCharater(3, 4, 5)
                || isSameCharater(6, 7, 8)
                || isSameCharater(0, 3, 6)
                || isSameCharater(1, 4, 7)
                || isSameCharater(2, 5, 8)
                || isSameCharater(0, 4, 8)
                || isSameCharater(2, 4, 6);
    }

    private boolean isSameCharater(int first, int second, int third) {
        return matrix[first] == matrix[second]
                && matrix[second] == matrix[third]
                && (matrix[first] == 'X' || matrix[first] == 'O');
    }
}
