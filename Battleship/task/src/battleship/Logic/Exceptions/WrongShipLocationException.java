package battleship.Logic.Exceptions;

public class WrongShipLocationException extends Exception {
    public WrongShipLocationException(String message) {
        super(message);
    }
}
