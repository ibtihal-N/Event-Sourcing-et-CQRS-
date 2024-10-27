package ma.enset.compte_cqrs_es.commands.aggregates;

import ma.enset.compte_cqrs_es.common_api.commands.CreateAccountCommand;
import ma.enset.compte_cqrs_es.common_api.commands.CreditAccountCommand;
import ma.enset.compte_cqrs_es.common_api.commands.DebitAccountCommand;
import ma.enset.compte_cqrs_es.common_api.enums.AccountStatus;
import ma.enset.compte_cqrs_es.common_api.events.AccountActivatedEvent;
import ma.enset.compte_cqrs_es.common_api.events.AccountCreatedEvent;
import ma.enset.compte_cqrs_es.common_api.events.AccountCreditedEvent;
import ma.enset.compte_cqrs_es.common_api.events.AccountDebitedEvent;
import ma.enset.compte_cqrs_es.common_api.exceptions.AmountNegativeException;
import ma.enset.compte_cqrs_es.common_api.exceptions.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getInitialBalance()<0)throw new RuntimeException("Impossible ...");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId=event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status=AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
           event.getId(),
           AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status=event.getStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand command){
        if(command.getAmount()<0) throw new AmountNegativeException("Amount should not be negative");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance+=event.getAmount();
    }
    @CommandHandler
    public void handle(DebitAccountCommand command){
        if(command.getAmount()<0) throw new AmountNegativeException("Amount should not be negative");
        if(this.balance<command.getAmount()) throw new BalanceNotSufficientException("Balance not sufficient Exception=>"+balance);
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance-=event.getAmount();
    }
}
