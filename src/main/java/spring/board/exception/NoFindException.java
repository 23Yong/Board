package spring.board.exception;

public class NoFindException extends RuntimeException {
    public NoFindException() {
        super();
    }

    public NoFindException(String message) {
        super(message);
    }

    public NoFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFindException(Throwable cause) {
        super(cause);
    }
}
