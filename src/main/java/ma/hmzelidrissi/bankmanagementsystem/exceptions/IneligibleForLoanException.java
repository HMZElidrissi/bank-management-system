package ma.hmzelidrissi.bankmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class IneligibleForLoanException extends BaseException {
    public IneligibleForLoanException(String message, List<String> errors) {
        super(message, HttpStatus.BAD_REQUEST, errors);
    }
}
