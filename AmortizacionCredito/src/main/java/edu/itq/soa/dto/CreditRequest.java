package edu.itq.soa.dto;

import java.util.Map;

public record CreditRequest(
        String accountNumber,
        String clientName,
        double requestedAmount,
        //double suggestedAmount,
        int termMonths,
        double annualRate,
        double accountBalance,
        Map<Integer, Double> extraPayments
    ) {}
