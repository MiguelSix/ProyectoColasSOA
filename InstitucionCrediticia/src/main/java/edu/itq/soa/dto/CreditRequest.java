package edu.itq.soa.dto;

import java.util.Map;

public record CreditRequest (
        String accountNumber,
        String clientName,
        double requestedAmount,
        int termMonths,
        double annualRate,
        double AccountBalance,
        Map<Integer, Double> extraPayments
        )
{}