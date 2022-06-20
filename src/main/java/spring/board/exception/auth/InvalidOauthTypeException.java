package spring.board.exception.auth;

public class InvalidOauthTypeException extends RuntimeException {

    public InvalidOauthTypeException(String message) {
        super(message);
    }
}
