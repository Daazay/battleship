package battleship.Logic.Entities;

import battleship.Logic.Exceptions.InvalidCoordinatesException;
import battleship.Logic.Exceptions.InvalidSubmarineLengthException;
import battleship.Logic.Exceptions.ShipPlacedTooCloseException;
import battleship.Utils.Pair;

public class Field {
    private final String[][] grid;
    public Field() {
        this.grid = createField();
    }

    private String[][] createField() {
        String[][] grid = new String[10][10];
        for (int i = 0; i < 10; i++) {
            grid[i] = new String[10];
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = "~";
            }
        }
        return grid;
    }

    public boolean placeShip(Pair<Integer,Integer> f,Pair<Integer,Integer> s, Ship ship) throws ShipPlacedTooCloseException, InvalidCoordinatesException, InvalidSubmarineLengthException {
        // Check if coordinates are valid
        if (isCoordOutOfField(f) || isCoordOutOfField(s) || !isShipsLocationIsValid(f,s))
            throw new InvalidCoordinatesException("Wrong ship location!");
        // Check if ships length is valid
        if (!isShipsLengthIsValid(f,s,ship))
            throw new InvalidSubmarineLengthException("Wrong length of the Submarine!");
        // Check if ship is close to another one
        if (isShipCloseToAnotherOne(f,s))
            throw new ShipPlacedTooCloseException("You placed it too close to another one!");
        // Otherwise
        setCells(f,s,"O");
        return true;
    }

    public boolean fire(Pair<Integer,Integer> coord) throws InvalidCoordinatesException {
        // check if coordinates are valid
        if (isCoordOutOfField(coord))
            throw new InvalidCoordinatesException("You entered the wrong coordinates!");

        if (grid[coord.getFirst()][coord.getSecond()].equals("O") || grid[coord.getFirst()][coord.getSecond()].equals("X")) {
            grid[coord.getFirst()][coord.getSecond()] = "X";
            return true;
        } else {
            grid[coord.getFirst()][coord.getSecond()] = "M";
            return false;
        }
    }

    public boolean AreAllShipsCompletelyDestroyed() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid[i][j].equals("O")) return false;
            }
        }
        return true;
    }

    public boolean isShipDestroyed(Pair<Integer,Integer> coord) throws InvalidCoordinatesException {
        // check if coordinates are valid
        if (isCoordOutOfField(coord))
            throw new InvalidCoordinatesException("You entered the wrong coordinates!");

        int y = coord.getFirst();
        int x = coord.getSecond();

        // determine if the ship is horizontal or vertical
        boolean isVertical = false;
        if (y - 1 >= 0) {
            isVertical = grid[y - 1][x].equals("X") || grid[y - 1][x].equals("O");
        }
        if (y + 1 < 10) {
            isVertical = grid[y + 1][x].equals("X") || grid[y + 1][x].equals("O");
        }

        if(isVertical) {
            for(int i = y; i != 0; i--) {
                if(grid[i][x].equals("O"))
                    return false;
                if(grid[i][x].equals("~"))
                    break;
            }
            for(int i = y; i < 10; i++) {
                if(grid[i][x].equals("O"))
                    return false;
                if(grid[i][x].equals("~"))
                    break;
            }
        } else {
            for(int i = x; i != 0; i--) {
                if(grid[y][i].equals("O"))
                    return false;
                if(grid[y][i].equals("~"))
                    break;
            }
            for(int i = y; i < 10; i++) {
                if(grid[y][i].equals("O"))
                    return false;
                if(grid[y][i].equals("~"))
                    break;
            }
        }
        return true;
    }
    public void show(boolean withFog) {
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
                if (withFog && grid[i][j].equals("O")) {
                    System.out.print("~");
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println();
        }
    }
    private void setCells(Pair<Integer,Integer> f,Pair<Integer,Integer> s, String sign) {
        int fRow = Math.min(f.getFirst(), s.getFirst());
        int sRow = Math.max(f.getFirst(), s.getFirst());
        int fCol = Math.min(f.getSecond(), s.getSecond());
        int sCol = Math.max(f.getSecond(), s.getSecond());

        for (int i = fRow; i <= sRow; i++) {
            for (int j = fCol; j <= sCol; j++) {
                grid[i][j] = sign;
            }
        }
    }
    private boolean isShipCloseToAnotherOne(Pair<Integer,Integer> f,Pair<Integer,Integer> s) {
        int fRow = Math.min(f.getFirst(), s.getFirst());
        int sRow = Math.max(f.getFirst(), s.getFirst());
        int fCol = Math.min(f.getSecond(), s.getSecond());
        int sCol = Math.max(f.getSecond(), s.getSecond());

        for (int i = fRow - 1; i <= sRow + 1; i++) {
            for (int j = fCol - 1; j <= sCol + 1; j++) {
                if (i < 0 || j < 0 || i >= 10 || j >= 10) continue;
                if (grid[i][j].equals("O")) return true;
            }
        }
        return false;
    }
    private boolean isShipsLengthIsValid(Pair<Integer,Integer> f,Pair<Integer,Integer> s, Ship ship) {
        if (f.getFirst().equals(s.getFirst())) {
            return Math.abs(f.getSecond() - s.getSecond()) + 1 == ship.getLength();
        } else {
            return Math.abs(f.getFirst() - s.getFirst()) + 1 == ship.getLength();
        }
    }
    private boolean isShipsLocationIsValid(Pair<Integer,Integer> f,Pair<Integer,Integer> s) {
        if (!f.getFirst().equals(s.getFirst())) {
            return f.getSecond().equals(s.getSecond());
        } else {
            return !f.getSecond().equals(s.getSecond());
        }
    }
    private boolean isCoordOutOfField(Pair<Integer,Integer> coord) {
        return !((coord.getFirst() >= 0 && coord.getFirst() < 10) || (coord.getSecond() >= 0 && coord.getSecond() < 10));
    }
}
