package ma.hmzelidrissi.bankmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends BaseException{
    public InsufficientFundsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
