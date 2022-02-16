package spring.board.exception.member;

public class DuplicatedMemberException extends RuntimeException {
    public DuplicatedMemberException(String message) {
        super(message);
    }
}
