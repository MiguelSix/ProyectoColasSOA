package edu.itq.soa.dto;

import java.util.Map;

public record CreditValidationRequest(
        String clientId,
        String clientName,
        double requestedAmount,
        int termMonths,
        String accountNumber,
        double annualRate,
        double accountBalance,
        Map<Integer, Double> extraPayments
    ){}