package ma.enset.compte_cqrs_es.query.repositpry;

import ma.enset.compte_cqrs_es.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
