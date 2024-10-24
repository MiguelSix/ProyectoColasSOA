package edu.itq.soa.dto;

import java.util.List;

public record DepositResponse(
        String accountNumber,
        String clientName,
        double accountBalance,
        double loanAmount,
        int termMonths,
        double annualRate,
        double finalBalance,
        String status,
        String message,
        String transactionDate,
        double totalPayment,
        double totalInterest,
        List<AmortizationRow> amortizationTable
    ){
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
