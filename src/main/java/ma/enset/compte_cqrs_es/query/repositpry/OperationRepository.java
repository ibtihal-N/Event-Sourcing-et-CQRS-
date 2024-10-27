package ma.enset.compte_cqrs_es.query.repositpry;

import ma.enset.compte_cqrs_es.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
