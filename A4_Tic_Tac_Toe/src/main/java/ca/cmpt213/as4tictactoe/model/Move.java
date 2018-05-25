package ca.cmpt213.as4tictactoe.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A class representing each move the user makes
 *
 * @author Di Wang
 */
public class Move {
    private int moveNumber;
    private char piece;
    private int row;
    private int col;

    public Move() {
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public char getPiece() {
        return piece;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void throwInvalidMoveException(String errorMsg) {
        throw new InvalidMoveException(errorMsg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private class InvalidMoveException extends RuntimeException {
        InvalidMoveException(String msg) {
            super(msg);
        }
    }
}
