package ca.cmpt213.as4tictactoe.model;

/**
 * An enum representing each error after an invalid move is made
 *
 * @author Di Wang
 */
public enum MoveError {
    GAME_OVER("Additional moves not allowed: game is over"),
    INVALID_FIRST_MOVE("X must make the first move."),
    INVALID_PLAYER("Player may not play twice in a row."),
    INVALID_LOCATION("Invalid move location (must be 0-2 for row and column)"),
    INVALID_PIECE("Invalid piece (must be X or O)"),
    DUPLICATE_LOCATION("Invalid move location (duplicate of earlier move)");

    private String description;

    MoveError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
