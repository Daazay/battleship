package battleship.Logic;

import battleship.Utils.Pair;

public class Ship {
    private final String name;
    private final int length;

    private int health = 0;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.health = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public void hit() {
        health--;
    }

    public boolean isDestroyed() {
        return health == 0;
    }
}
