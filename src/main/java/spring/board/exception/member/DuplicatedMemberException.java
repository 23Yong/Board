package spring.board.exception.member;

public class DuplicatedMemberException extends RuntimeException {

    public static DuplicatedMemberException createDuplicatedMemberException() {
        return new DuplicatedMemberException("이미 존재하는 회원입니다.");
    }

    public DuplicatedMemberException(String message) {
        super(message);
    }
}
