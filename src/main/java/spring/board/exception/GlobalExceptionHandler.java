package spring.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.board.exception.member.CredentialException;
import spring.board.exception.member.UnauthenticatedUserException;
import spring.board.exception.member.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthenticatedUserException.class)
    public final ResponseEntity<String> handleUnauthenticatedUserException(
            UnauthenticatedUserException ex) {
        return new ResponseEntity<>("Unauthenticated user", HttpStatus.UNAUTHORIZED);
    }
}
