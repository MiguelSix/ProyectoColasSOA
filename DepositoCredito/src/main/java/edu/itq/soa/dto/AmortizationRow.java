package edu.itq.soa.dto;

public record AmortizationRow(
        int paymentNumber,
        double payment,
        double principal,
        double interest,
        double balance) 
{

}