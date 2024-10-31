package ma.hmzelidrissi.bankmanagementsystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
    private final HttpStatus status;

    protected BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
