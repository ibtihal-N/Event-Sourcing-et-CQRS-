package ma.enset.compte_cqrs_es.common_api.exceptions;



public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
