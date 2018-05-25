package com.wang.model;

import java.util.*;

/**
 * a class for manipulating board such as placing tanks, destroying tanks/cells
 *
 * @author Di Wang & Xin Wang
 */
public class Board {
    private static Random random = new Random();
    private Cell[][] grid;
    private int rowNum;
    private int colNum;
    private List<Tank> tanks;
    private List<Integer> unoccupiedCellIds; //keeping track of which cell is not occupied by a tank by id

    public Board(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        grid = new Cell[rowNum][colNum];
        unoccupiedCellIds = new ArrayList<>();
        tanks = new ArrayList<>();
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                int cellId = i * colNum + j;
                grid[i][j] = new Cell(i, j);
                unoccupiedCellIds.add(Integer.valueOf(cellId));
            }
        }
    }

    private static int generateRandomIntBetween(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public void createBoard(int numberOfTanks) {
        for (int i = 0; i < numberOfTanks; i++) {
            Tank tank = new Tank(i);
            randomlyPlaceTank(tank);
        }
    }

    private void randomlyPlaceTank(Tank tank) {
        boolean isTankPlaced = false;
        while (!isTankPlaced) {
            assert unoccupiedCellIds.size() > 3 : "No more tank can be placed in the board";
            int randomUnoccupiedCellId = unoccupiedCellIds
                    .get(generateRandomIntBetween(0, unoccupiedCellIds.size() - 1));

            Cell cell = getCellById(randomUnoccupiedCellId);
            assert !cell.isTankCell() : "The cell " + cell.getRow()
                    + ", " + cell.getCol() + " should not have been occupied";

            List<Cell> tankCells = new ArrayList<>();
            tankCells.add(cell);

            if (randomlyGrowCells(tankCells) && tankCells.size() == 4) {
                tank.setTankCells(tankCells);
                for (Cell validCell : tankCells) {
                    validCell.setTank(tank);
                }
                tanks.add(tank);
                isTankPlaced = true;
            }
            for (Cell traversedCell : tankCells) {
                unoccupiedCellIds.remove(Integer.valueOf(traversedCell.getId()));
            }
        }
    }

    private Cell getCellById(int id) {
        int row = id / colNum;
        int col = id % colNum;
        return grid[row][col];
    }

    /**
     * if all cells can be created, return true
     * else, return false
     * temp: used as a shallow copy of cells to keep track of the traversed cells
     */
    private boolean randomlyGrowCells(List<Cell> cells) {
        assert cells.size() == 1;
        List<Cell> temp = new ArrayList<>(cells);
        while (cells.size() < 4) {
            boolean isNewTankCellPlaced = false;
            while (!isNewTankCellPlaced) {
                int randomIndex = generateRandomIntBetween(0, temp.size() - 1);
                Cell cell = temp.get(randomIndex);
                List<Directions> validDirections = validateDirections(cell, cells);
                if (validDirections.isEmpty()) {
                    temp.remove(randomIndex);
                    if (temp.isEmpty()) {
                        return false;
                    }
                } else {
                    int randomDirectionIndex = generateRandomIntBetween(0, validDirections.size() - 1);
                    Directions randomDirection = validDirections.get(randomDirectionIndex);
                    Cell nextCell = grid[cell.getRow() + randomDirection.getRowOffset()]
                            [cell.getCol() + randomDirection.getColOffset()];
                    cells.add(nextCell);
                    temp.add(nextCell);
                    isNewTankCellPlaced = true;
                }
            }
        }
        return true;
    }

    private List<Directions> validateDirections(Cell cell, List<Cell> cellList) {
        List<Directions> directionList = new ArrayList<>(Arrays.asList(Directions.values()));
        Iterator<Directions> itr = directionList.iterator();
        List<Directions> removalList = new ArrayList<>();
        while (itr.hasNext()) {
            Directions direction = itr.next();
            if (cell.getRow() + direction.getRowOffset() >= 0
                    && cell.getRow() + direction.getRowOffset() < rowNum
                    && cell.getCol() + direction.getColOffset() >= 0
                    && cell.getCol() + direction.getColOffset() < colNum) {
                Cell potentialCell =
                        grid[cell.getRow() + direction.getRowOffset()][cell.getCol() + direction.getColOffset()];
                Set<Cell> cellSet = new HashSet<>(cellList);
                if (potentialCell.isTankCell() || cellSet.contains(potentialCell)) {
                    removalList.add(direction);
                }
            } else {
                removalList.add(direction);
            }
        }
        directionList.removeAll(removalList);
        return directionList;
    }

    public boolean eliminateCell(int[] cellLocation) {
        assert cellLocation.length == 2;
        Cell targetCell = grid[cellLocation[0]][cellLocation[1]];
        return targetCell.sufferAttack();
    }

    public int calculateTotalDamage() {
        int totalDamage = 0;
        for (Tank tank : tanks) {
            totalDamage += tank.getDamage();
        }
        return totalDamage;
    }

    public int getNumOfTanks() {
        return tanks.size();
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    private enum Directions {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        private int rowOffset;
        private int colOffset;

        Directions(int rowOffset, int colOffset) {
            this.rowOffset = rowOffset;
            this.colOffset = colOffset;
        }

        public int getRowOffset() {
            return rowOffset;
        }

        public int getColOffset() {
            return colOffset;
        }
    }
}
