package spring.board.exception.reply;

public class ReplyNotFoundException extends RuntimeException {

    public ReplyNotFoundException(String message) {
        super(message);
    }

    public ReplyNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReplyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
