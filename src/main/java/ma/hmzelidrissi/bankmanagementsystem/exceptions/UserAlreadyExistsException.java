package ma.hmzelidrissi.bankmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT); // 409
    }
}
