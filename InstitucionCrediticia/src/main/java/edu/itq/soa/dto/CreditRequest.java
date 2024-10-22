package edu.itq.soa.dto;

import java.util.Map;

public record CreditRequest (
        String clientId,
        String clientName,
        double requestedAmount,
        int termMonths,
        String accountNumber,
        double annualRate,
        double AccountBalance,
        Map<Integer, Double> extraPayments
        )
{}