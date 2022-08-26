package battleship.Logic;

import battleship.Utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Ship> ships;

    Player() {
        ships = new ArrayList<>();
    }

    public void placeShip(Ship ship) {
        ships.add(ship);
    }

    public List<Ship> getShips() {
        return ships;
    }
}
