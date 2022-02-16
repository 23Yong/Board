package spring.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.board.exception.member.UnauthenticatedUserException;
import spring.board.exception.member.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthenticatedUserException.class)
    public final ResponseEntity<String> handleUnauthenticatedUserException(
            UnauthenticatedUserException ex) {
        return new ResponseEntity<>("Unauthenticated user", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<String> handleUserNotFoundException(
            UserNotFoundException ex) {
        return new ResponseEntity<>("가입하지 않거나, 잘못된 비밀번호 입니다.", HttpStatus.NOT_FOUND);
    }
}
