package edu.itq.soa.dto;

public record CreditResponse(
        String accountNumber,
        String clientName,
        double accountBalance,
        double loanAmount,
        double finalBalance,
        String status,
        String message
    ) {}
