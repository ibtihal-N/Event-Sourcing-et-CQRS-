package ma.enset.compte_cqrs_es.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.compte_cqrs_es.common_api.enums.OperationType;
import ma.enset.compte_cqrs_es.common_api.events.AccountActivatedEvent;
import ma.enset.compte_cqrs_es.common_api.events.AccountCreatedEvent;
import ma.enset.compte_cqrs_es.common_api.events.AccountCreditedEvent;
import ma.enset.compte_cqrs_es.common_api.events.AccountDebitedEvent;
import ma.enset.compte_cqrs_es.common_api.queries.GetAllAccountQuery;
import ma.enset.compte_cqrs_es.query.entities.Account;
import ma.enset.compte_cqrs_es.query.entities.Operation;
import ma.enset.compte_cqrs_es.query.repositpry.AccountRepository;
import ma.enset.compte_cqrs_es.query.repositpry.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    private void on(AccountCreatedEvent event) {
        log.info("*******************");
        log.info("AccountCreatedEvent received");
        Account account=new Account();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        account.setBalence(event.getInitialBalance());
        accountRepository.save(account);
    }

    @EventHandler
    private void on(AccountActivatedEvent event) {
        log.info("*******************");
        log.info("AccountCreatedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    private void on(AccountDebitedEvent event) {
        log.info("*******************");
        log.info("AccountCreatedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalence(account.getBalence()+event.getAmount());
        accountRepository.save(account);
    }
    @EventHandler
    private void on(AccountCreditedEvent event) {
        log.info("*******************");
        log.info("AccountCreatedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalence(account.getBalence()-event.getAmount());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccountQuery query){
        return accountRepository.findAll();
    }
}
