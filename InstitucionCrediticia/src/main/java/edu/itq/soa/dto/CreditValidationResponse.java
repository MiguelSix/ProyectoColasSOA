package edu.itq.soa.dto;

import java.util.Map;

public record CreditValidationResponse(
        String accountNumber,
        String clientName,
        boolean approved,
        String reason,
        double requestedAmount,
        int termMonths,
        double annualRate,
        double accountBalance,
        Map<Integer, Double> extraPayments
    ) {}
