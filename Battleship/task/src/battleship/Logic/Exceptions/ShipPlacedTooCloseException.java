package battleship.Logic.Exceptions;

public class ShipPlacedTooCloseException extends Exception {
    public ShipPlacedTooCloseException(String message) {
        super(message);
    }
}
