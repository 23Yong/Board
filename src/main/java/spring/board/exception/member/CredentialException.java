package spring.board.exception.member;

public class CredentialException extends RuntimeException {

    public static CredentialException createCredentialException() {
        return new CredentialException("찾으려는 회원이 없습니다.");
    }

    public CredentialException(String message) {
        super(message);
    }
}
