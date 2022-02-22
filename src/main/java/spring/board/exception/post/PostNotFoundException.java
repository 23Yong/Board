package spring.board.exception.post;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message) {
        super(message);
    }
}
