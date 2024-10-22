package edu.itq.soa.dto;

import java.util.List;

public record AmortizationResponse(
        String accountNumber,
        String clientName,
        double loanAmount,
        int termMonths,
        double annualRate,
        double accountBalance,
        List<AmortizationRow> amortizationTable
    ) {
        public double getTotalPayment() {
            return amortizationTable.stream()
                .mapToDouble(row -> row.monthlyPayment() + row.extraPayment())
                .sum();
        }
        
        public double getTotalInterest() {
            return amortizationTable.isEmpty() ? 
                0.0 : 
                amortizationTable.get(amortizationTable.size() - 1).totalInterest();
        }
    }