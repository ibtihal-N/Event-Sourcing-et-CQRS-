package ma.enset.compte_cqrs_es.common_api.dto;

import lombok.Data;

@Data
public class DebitAccountRequestDTO {
    private String accountId;
    private double amount;
    private String currency;
}
