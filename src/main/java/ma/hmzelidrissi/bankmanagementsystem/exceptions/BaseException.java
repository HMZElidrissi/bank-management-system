package ma.hmzelidrissi.bankmanagementsystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public abstract class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final List<String> details;

    protected BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.details = null;
    }

    protected BaseException(String message, HttpStatus status, List<String> details) {
        super(message);
        this.status = status;
        this.details = details;
    }
}
