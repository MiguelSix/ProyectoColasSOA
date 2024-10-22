package edu.itq.soa.dto;

public record AmortizationRow(
        int paymentNumber,
        double monthlyPayment,
        double interest,
        double capital,
        double extraPayment,
        double currentBalance,
        double totalInterest) 
{}