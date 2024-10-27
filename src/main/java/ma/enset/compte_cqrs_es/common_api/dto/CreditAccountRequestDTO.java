package ma.enset.compte_cqrs_es.common_api.dto;

import lombok.Data;

@Data
public class CreditAccountRequestDTO {
    private String accountId;
    private double amount;
    private String currency;
}
