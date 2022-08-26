package battleship.Logic.Entities;

public class Ship {
    private final String name;
    private final int length;
    private int health;

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

    public int getHealth() {
        return health;
    }

    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
