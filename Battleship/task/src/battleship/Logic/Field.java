package battleship.Logic;

/*
    Aircraft Carrier is 5 cells,
    Battleship is 4 cells,
    Submarine is 3 cells,
    Cruiser is also 3 cells,
    and Destroyer is 2 cells.
*/

import battleship.Logic.Exceptions.InvalidCoordinatesException;
import battleship.Logic.Exceptions.InvalidSubmarineLengthException;
import battleship.Logic.Exceptions.ShipPlacedTooCloseException;
import battleship.Utils.Pair;

public class Field {
    private final String[][] grid;

    public Field() {
        grid = new String[10][10];
        for (int i = 0; i < 10; i++) {
            grid[i] = new String[11];
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = "~";
            }
        }
    }

    private boolean isCoordinatesValid(int x, int y) {
        return ((x >= 0 && x < 10) && (y >= 0 && y < 10));
    }

    public boolean isShipLocationValid(Pair<int[],int[]> coord) {
        int fx = coord.getFirst()[0];
        int fy = coord.getFirst()[1];
        int sx = coord.getSecond()[0];
        int sy = coord.getSecond()[1];
        if (fx != sx) {
            return fy == sy;
        } else {
            return fy != sy;
        }
    }

    public boolean fire(int[] coordinates) throws InvalidCoordinatesException {
        // check if coordinates are valid
        if (!isCoordinatesValid(coordinates[0],coordinates[1]))
            throw new InvalidCoordinatesException("You entered the wrong coordinates!");

        if (grid[coordinates[0]][coordinates[1]].equals("O")) {
            grid[coordinates[0]][coordinates[1]] = "X";
            return true;
        } else {
            grid[coordinates[0]][coordinates[1]] = "M";
            return false;
        }
    }

    public void placeShip(Pair<int[],int[]> coordinates, Ship ship) throws InvalidCoordinatesException, InvalidSubmarineLengthException, ShipPlacedTooCloseException {
        int minRow = Math.min(coordinates.getFirst()[0], coordinates.getSecond()[0]);
        int maxRow = Math.max(coordinates.getFirst()[0], coordinates.getSecond()[0]);
        int minCol = Math.min(coordinates.getFirst()[1], coordinates.getSecond()[1]);
        int maxCol = Math.max(coordinates.getFirst()[1], coordinates.getSecond()[1]);

        // check if coordinates diagonal
        // check if coordinates is valid

        if (!isShipLocationValid(coordinates) || !(isCoordinatesValid(coordinates.getFirst()[0],coordinates.getSecond()[0])
                && isCoordinatesValid(coordinates.getFirst()[1],coordinates.getSecond()[1])))
            throw new InvalidCoordinatesException("Wrong ship location!");

/*        if(isCoordinatesDiagonal(minRow, maxRow, minCol, maxCol))
            throw new InvalidCoordinatesException("Wrong ship location!");*/
        // check if the length of the ship is longer than it should be
        if(isShipsLengthValid(minRow, maxRow, minCol, maxCol, ship))
            throw new InvalidSubmarineLengthException("Wrong length of the Submarine!");
        // check if the ship is close to another
        if(isShipCloseToAnotherOne(minRow, maxRow, minCol, maxCol))
            throw new ShipPlacedTooCloseException("You placed it too close to another one.");
        // place ship
        setCells(minRow, maxRow, minCol, maxCol, "O");
    }
    private boolean isShipCloseToAnotherOne(int fRow, int sRow, int fCol, int sCol) {
        for (int i = fRow - 1; i <= sRow + 1; i++) {
            for (int j = fCol - 1; j <= sCol + 1; j++) {
                if (i < 0 || j < 0 || i >= 10 || j >= 10) continue;
                if (grid[i][j].equals("O")) return true;
            }
        }
        return false;
    }

    private boolean isShipsLengthValid(int fRow, int sRow, int fCol, int sCol, Ship ship) {
        if (fRow == sRow) {
            return !(sCol - fCol + 1 == ship.getLength());
        } else if (fCol == sCol) {
            return !(sRow - fRow + 1 == ship.getLength());
        }
        return false;
    }

    private boolean isCoordinatesDiagonal(int fRow, int sRow, int fCol, int sCol) {
        return !((fRow == sRow || fCol == sCol));
    }

    public void showWithFog() {
        System.out.print("  ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print((char)('A' + i));
            for (int j = 0; j < 10; j++) {
                System.out.print(" ");
                if (!grid[i][j].equals("O"))
                    System.out.print(grid[i][j]);
                else
                    System.out.print("~");
            }
            System.out.println();
        }
        System.out.println();
    }
    public void show() {
        System.out.print("  ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print((char)('A' + i));
            for (int j = 0; j < 10; j++) {
                System.out.print(" ");
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setCells(int fRow, int sRow, int fCol, int sCol, String cell) {
        for (int i = fRow; i <= sRow; i++) {
            for (int j = fCol; j <= sCol; j++) {
                grid[i][j] = cell;
            }
        }
    }
}
