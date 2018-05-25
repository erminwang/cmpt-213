package ca.cmpt213.as4tictactoe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for game logic and managing Board/Move classes
 *
 * @author Di Wang
 */
public class Game {

    private long id;
    private String description;
    private String gameState = GameStatus.PLAYING.name();
    private List<Move> moves = new ArrayList<>();
    private Board board = new Board();

    public Game() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    // place user move into board and check game status, return true if game is over, reuturn false if not
    public boolean placeMove(Move move) {
        boolean isPieceCharacterValid = Character.toUpperCase(move.getPiece()) == 'X'
                || Character.toUpperCase(move.getPiece()) == 'O';
        boolean isOddMoveValid = moves.size() % 2 == 0 && Character.toUpperCase(move.getPiece()) == 'X';
        boolean isEvenMoveValid = moves.size() % 2 == 1 && Character.toUpperCase(move.getPiece()) == 'O';

        if (moves.size() == 0 && Character.toUpperCase(move.getPiece()) != 'X') {
            move.throwInvalidMoveException(MoveError.INVALID_FIRST_MOVE.getDescription());
        } else if (!gameState.equals(GameStatus.PLAYING.name())) {
            move.throwInvalidMoveException(MoveError.GAME_OVER.getDescription());
        } else if (!isPieceCharacterValid) {
            move.throwInvalidMoveException(MoveError.INVALID_PIECE.getDescription());
        } else if (!isOddMoveValid && !isEvenMoveValid) {
            move.throwInvalidMoveException(MoveError.INVALID_PLAYER.getDescription());
        } else if (move.getRow() < 0 || move.getRow() > 2 || move.getCol() < 0 || move.getCol() > 2) {
            move.throwInvalidMoveException(MoveError.INVALID_LOCATION.getDescription());
        } else if (board.isOccupied(move.getRow(), move.getCol())) {
            move.throwInvalidMoveException(MoveError.DUPLICATE_LOCATION.getDescription());
        }

        move.setMoveNumber(moves.size() + 1);
        moves.add(move);
        return board.placePieceAndCheckStatus(move.getPiece(), move.getRow(), move.getCol());
    }
}
