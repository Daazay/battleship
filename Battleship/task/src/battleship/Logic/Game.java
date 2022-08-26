package battleship.Logic;

import battleship.Utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    Scanner scanner;
    private final Field field;
    private final Player player1;
    private final Player player2;

    private boolean isGameover = false;

    public Game() {
        field = new Field();
        player1 = new Player();
        player2 = new Player();
        scanner = new Scanner(System.in);
    }

    private int[] getCoordinate(String coordinate) {
        return new int[]{coordinate.charAt(0) - 65, Integer.parseInt(coordinate.substring(1)) - 1};
    }

    private void placeShip(Ship ship) {
        System.out.println("Enter the coordinates of the " + ship.getName() + "(" + ship.getLength() +" cells):");
        boolean isShipPlaced = false;
        while(!isShipPlaced) {
            try {
                field.placeShip(new Pair<>(getCoordinate(scanner.next()), getCoordinate(scanner.next())), ship);
                player1.placeShip(ship);
                isShipPlaced = true;
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + "Try again:");
            }
        }
    }

    private boolean fire() {
        System.out.println("Take a shot!");
        while(true) {
            try {
                return field.fire(getCoordinate(scanner.next()));
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + "Try again:");
            }
        }
    }
/*    private void fire() {
        boolean move = false;
        while (true) {
            try {
                if(move) {
                    String coordinates = scanner.next();
                    boolean isHitted = field.fire(getCoordinate(coordinates));
                    field.show();
                    if(isHitted) {
                        System.out.println("You hit a ship!");
                    } else {
                        System.out.println("You missed!");
                    }
                    move = false;
                } else {
                    System.out.println("Take a shot!");
                    move = true;
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + "Try again:");
            }
        }
    }*/

    public void Run() {
        List<Ship> firstPlayerShips = Arrays.asList(new Ship("Aircraft Carrier", 5),
                                                    new Ship("Battleship", 4),
                                                    new Ship("Submarine", 3),
                                                    new Ship("Cruiser", 3),
                                                    new Ship("Destroyer", 2));
        field.show();
        for(int i = 0; i < firstPlayerShips.size(); i++) {
            placeShip(firstPlayerShips.get(i));
            field.show();
        }
        System.out.println("The game starts!");
        field.showWithFog();
        boolean isHitted = fire();
        field.showWithFog();
        if(isHitted) {
            System.out.println("You hit a ship!");
        } else {
            System.out.println("You missed!");
        }
        field.show();
    }
}
