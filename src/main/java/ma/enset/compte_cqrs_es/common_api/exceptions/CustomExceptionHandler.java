package ma.enset.compte_cqrs_es.common_api.exceptions;

import ma.enset.compte_cqrs_es.common_api.exceptions.AmountNegativeException;
import ma.enset.compte_cqrs_es.common_api.exceptions.BalanceNotSufficientException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(AmountNegativeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAmountNegativeException(AmountNegativeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BalanceNotSufficientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBalanceNotSufficientException(BalanceNotSufficientException ex) {
        return ex.getMessage();
    }
}
