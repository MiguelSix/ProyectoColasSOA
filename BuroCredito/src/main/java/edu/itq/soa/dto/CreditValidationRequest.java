package edu.itq.soa.dto;

import java.util.Map;

public record CreditValidationRequest(
        String accountNumber,
        String clientName,
        double requestedAmount,
        int termMonths,
        double annualRate,
        double accountBalance,
        Map<Integer, Double> extraPayments
    ){}