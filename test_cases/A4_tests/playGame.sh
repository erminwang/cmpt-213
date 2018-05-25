#!/bin/sh -x
SERVER=localhost:8080


#################################################
#   General
#################################################
# Read /about
curl -i -H "Content-Type: application/json" \
    -X GET ${SERVER}/about

#################################################
#   GAME
#################################################
# Make a game
curl -i -H "Content-Type: application/json" \
    -X POST -d '{
        "description": "My first game!"
    }' ${SERVER}/games

curl -i -H "Content-Type: application/json" \
    -X POST -d '{
        "description": "Second game"
    }' ${SERVER}/games

# List all games
curl -i -H "Content-Type: application/json" \
    -X GET ${SERVER}/games

# Get a game
curl -i -H "Content-Type: application/json" \
    -X GET ${SERVER}/games/1

# Get board
curl -i -H "Content-Type: application/json" \
    -X GET ${SERVER}/games/1/board


#################################################
#   Moves
#################################################

# Make a move
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 0, "col": 0
    }' ${SERVER}/games/1/moves
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 1, "col": 0
    }' ${SERVER}/games/1/moves
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 1, "col": 1
    }' ${SERVER}/games/1/moves
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 0, "col": 1
    }' ${SERVER}/games/1/moves
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 2, "col": 2
    }' ${SERVER}/games/1/moves

# List all moves
curl -i -H "Content-Type: application/json" \
    -X GET ${SERVER}/games/1/moves

# Get board
curl -i -H "Content-Type: application/json" \
    -X GET ${SERVER}/games/1/board


# Get a game
curl -i -H "Content-Type: application/json" \
    -X GET ${SERVER}/games/1
