package edu.itq.soa.dto;

public record DepositRequest(
        String accountNumber,
        String clientName,
        double loanAmount,
        double accountBalance,
        String currency,
        String transactionId
    ) {}
