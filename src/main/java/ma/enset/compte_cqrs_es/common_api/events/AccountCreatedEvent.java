package ma.enset.compte_cqrs_es.common_api.events;

import lombok.Getter;
import ma.enset.compte_cqrs_es.common_api.enums.AccountStatus;

public class AccountCreatedEvent extends BaseEvent<String>{
    @Getter private double initialBalance;
    @Getter private String currency;
    @Getter private AccountStatus status;

    public AccountCreatedEvent(String id, double initialBalance, String currency, AccountStatus status) {
        super(id);
        this.status = status;
        this.currency = currency;
        this.initialBalance = initialBalance;
    }

}
