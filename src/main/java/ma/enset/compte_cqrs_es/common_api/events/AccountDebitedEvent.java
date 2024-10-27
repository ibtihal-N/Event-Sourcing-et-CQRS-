package ma.enset.compte_cqrs_es.common_api.events;

import lombok.Getter;

public class AccountDebitedEvent extends BaseEvent<String>{
    @Getter private double amount;
    @Getter private String currency;

    public AccountDebitedEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }

}
