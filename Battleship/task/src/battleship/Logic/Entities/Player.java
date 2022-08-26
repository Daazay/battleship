package battleship.Logic.Entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private List<Ship> ships;

    public Player() {
        this.ships = Arrays.asList(new Ship("Aircraft Carrier", 5),
                                    new Ship("Battleship", 4),
                                    new Ship("Submarine", 3),
                                    new Ship("Cruiser", 3),
                                    new Ship("Destroyer", 2));
    }

    public List<Ship> getShips() {
        return ships;
    }
}
