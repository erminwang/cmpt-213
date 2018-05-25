#!/bin/sh -x
SERVER=localhost:8080



#################################################
#   ERRORS
#################################################
# Start by rebooting server to reset game numbering and states
# Make a game
curl -i -H "Content-Type: application/json" \
    -X POST -d '{
        "description": "My first game!"
    }' ${SERVER}/games

# Turn order: X Goes first
echo "Should fail: X Goes first"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 0, "col": 0
    }' ${SERVER}/games/1/moves


# Turn order: X cannot go twice in a row
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 0, "col": 0
    }' ${SERVER}/games/1/moves
echo "Should fail: Player cannot go twice in a row"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 0, "col": 1
    }' ${SERVER}/games/1/moves
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 0, "col": 1
    }' ${SERVER}/games/1/moves


# Out of bounds moves
echo "Should fail: Invalid move"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": -1, "col": 0
    }' ${SERVER}/games/1/moves
echo "Should fail: Invalid move"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 3, "col": 0
    }' ${SERVER}/games/1/moves
echo "Should fail: Invalid move"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 0, "col": -1
    }' ${SERVER}/games/1/moves
echo "Should fail: Invalid move"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 0, "col": 3
    }' ${SERVER}/games/1/moves

# Invalid piece type
echo "Should fail: invalid piece"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "W",  "row": 0, "col": 0
    }' ${SERVER}/games/1/moves

# Duplicate location
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 1, "col": 0
    }' ${SERVER}/games/1/moves
echo "Should fail: Duplicate location played"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 1, "col": 0
    }' ${SERVER}/games/1/moves

# Move after game over.
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 2, "col": 2
    }' ${SERVER}/games/1/moves
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 1, "col": 2
    }' ${SERVER}/games/1/moves

curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 2, "col": 1
    }' ${SERVER}/games/1/moves
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 1, "col": 1
    }' ${SERVER}/games/1/moves

curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 2, "col": 0
    }' ${SERVER}/games/1/moves
echo "Should fail: Game over"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "O",  "row": 1, "col": 0
    }' ${SERVER}/games/1/moves
echo "Should fail: Game over (or turn order)"
curl -i -H "Content-Type: application/json" -X POST -d '{
        "piece": "X",  "row": 0, "col": 2
    }' ${SERVER}/games/1/moves

# Get board
curl -i -H "Content-Type: application/json" \
    -X GET ${SERVER}/games/1/board


# Game not found
echo "Should fail: Game not found"
curl -i -H "Content-Type: application/json" -X GET ${SERVER}/games/9999
echo "Should fail: Game not found"
curl -i -H "Content-Type: application/json" -X GET ${SERVER}/games/9999/moves
