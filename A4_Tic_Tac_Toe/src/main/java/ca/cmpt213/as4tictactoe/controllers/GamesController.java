package ca.cmpt213.as4tictactoe.controllers;

import ca.cmpt213.as4tictactoe.model.Board;
import ca.cmpt213.as4tictactoe.model.Game;
import ca.cmpt213.as4tictactoe.model.GameStatus;
import ca.cmpt213.as4tictactoe.model.Move;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The controller of all games
 *
 * @author Di Wang
 */
@RestController
public class GamesController {
    private List<Game> games = new ArrayList<>();
    private AtomicLong nextGameId = new AtomicLong();

    @GetMapping("/about")
    public String getAboutMessage() {
        return "Awesome Tic Tac Toe game written by Eddie!";
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createNewGame(@RequestBody Game game) {
        game.setId(nextGameId.incrementAndGet());
        games.add(game);
        return game;
    }

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return games;
    }

    @GetMapping("/games/{id}")
    public Game getGameById(@PathVariable("id") long gameId) {
        for (Game game : games) {
            if (game.getId() == gameId) {
                return game;
            }
        }

        throw new ResourceNotFoundException("Game for ID " + gameId + " not found.");
    }

    @GetMapping("/games/{id}/moves")
    public List<Move> getMovesByGameId(@PathVariable("id") long gameId) {
        for (Game game : games) {
            if (game.getId() == gameId) {
                return game.getMoves();
            }
        }

        throw new ResourceNotFoundException("Game for ID " + gameId + " not found.");
    }

    @PostMapping("/games/{id}/moves")
    @ResponseStatus(HttpStatus.CREATED)
    public Move createNewMove(@PathVariable("id") long gameId,
                              @RequestBody Move move) {
        for (Game game : games) {
            if (game.getId() == gameId) {
                if (game.placeMove(move)) {
                    if (move.getPiece() == 'X') {
                        game.setGameState(GameStatus.X_WON.name());
                    } else {
                        game.setGameState(GameStatus.O_WON.name());
                    }
                }
            }
        }
        return move;
    }

    @GetMapping("/games/{id}/board")
    public Board getBoardByGameId(@PathVariable("id") long gameId) {
        for (Game game : games) {
            if (game.getId() == gameId) {
                return game.getBoard();
            }
        }

        throw new ResourceNotFoundException("Game for ID " + gameId + " not found.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException(String msg) {
            super(msg);
        }
    }
}
