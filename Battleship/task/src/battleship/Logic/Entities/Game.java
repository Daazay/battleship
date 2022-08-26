package battleship.Logic.Entities;

import battleship.Utils.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Scanner scanner;
    private final Field fField;
    private final Field sField;
    private boolean isGameOver;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.fField = new Field();
        this.sField = new Field();
        this.isGameOver = false;
    }
    public void Play() {
        boolean isFirstPlayer = true;
        boolean isGameStarts = true;
        boolean isShipsPlaced = false;
        while(!isGameOver) {
            if(isFirstPlayer) {
                if (!isShipsPlaced) {
                    System.out.println("Player 1, place your ships on the game field");
                    placeShip(fField);
                    clearScreen();
                    isFirstPlayer = !isFirstPlayer;
                    continue;
                }
                if(isGameStarts) {
                    System.out.println("The game starts!");
                    isGameStarts = false;
                }
                printBoards(sField,fField);
                System.out.println("Player 1, it's your turn:");
                fire("1" ,sField);
                if(isGameOver) break;
                clearScreen();
                isFirstPlayer = !isFirstPlayer;
            } else {
                if (!isShipsPlaced) {
                    System.out.println("Player 2, place your ships on the game field");
                    placeShip(sField);
                    clearScreen();
                    isFirstPlayer = !isFirstPlayer;
                    isShipsPlaced = true;
                    continue;
                }
                printBoards(fField,sField);
                System.out.println("Player 2, it's your turn:");
                fire("2" ,fField);
                if(isGameOver) break;
                clearScreen();
                isFirstPlayer = !isFirstPlayer;
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private void printBoards(Field f, Field s) {
        f.show(true);
        System.out.println("---------------------");
        s.show(false);
    }
    private void clearScreen() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void placeShip(Field field) {
        List<Ship> shipList = Arrays.asList(new Ship("Aircraft Carrier", 5),
                new Ship("Battleship", 4),
                new Ship("Submarine", 3),
                new Ship("Cruiser", 3),
                new Ship("Destroyer", 2));

        field.show(false);

        for(int i = 0; i < shipList.size(); i++) {
            System.out.println("Enter the coordinates of the " + shipList.get(i).getName() + "(" + shipList.get(i).getLength() +" cells):");
            while(true) {
                try {
                    Pair<Integer,Integer> fCoord = getCoordinatesFromString(scanner.next());
                    Pair<Integer,Integer> sCoord = getCoordinatesFromString(scanner.next());
                    field.placeShip(fCoord, sCoord, shipList.get(i));
                    field.show(false);
                    break;
                } catch (Exception e) {
                    System.out.println("Error! " + e.getMessage() + "Try again:");
                }
            }
        }
    }

    private void fire(String player, Field field) {
        while(true) {
            try {
                Pair<Integer, Integer> coord = getCoordinatesFromString(scanner.next());
                boolean isHitted = field.fire(coord);
                if (isHitted) {
                    if (field.isShipDestroyed(coord)) {
                        if (field.AreAllShipsCompletelyDestroyed()) {
                            isGameOver = true;
                        } else {
                            System.out.println("You sank a ship! Specify a new target:");
                        }
                    } else {
                        System.out.println("You hit a ship!");
                    }
                } else {
                    System.out.println("You missed!");
                }
                return;
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + "Try again:");
            }
        }
    }

    private Pair<Integer,Integer> getCoordinatesFromString(String coordinates) {
        return new Pair<>(coordinates.charAt(0) - 65, Integer.parseInt(coordinates.substring(1)) - 1);
    }
}
