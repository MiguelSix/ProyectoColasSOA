package edu.itq.soa.dto;

public record CreditDepositResponse(
        String accountNumber,
        String clientName,
        double accountBalance,
        double loanAmount,
        double finalBalance,
        String status,
        String message
    ) {}